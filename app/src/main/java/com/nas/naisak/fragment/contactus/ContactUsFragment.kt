package com.nas.naisak.fragment.contactus

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.nas.naisak.R
import com.nas.naisak.constants.ApiClient
import com.nas.naisak.constants.CommonMethods
import com.nas.naisak.fragment.contactus.adapter.ContactUsAdapter
import com.nas.naisak.fragment.contactus.model.Contact
import com.nas.naisak.fragment.contactus.model.Contactusresponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactUsFragment : Fragment(), LocationListener,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    GoogleMap.OnMarkerDragListener,
    GoogleMap.OnMapLongClickListener {
    lateinit var mContext: Context
    lateinit var description: TextView
    lateinit var titleTextView: TextView
    lateinit var progress: ProgressBar
    lateinit var latitude: String
    lateinit var longitude: String
    lateinit var c_latitude: String
    lateinit var c_longitude: String
    lateinit var contactusRecycler: RecyclerView
    lateinit var locationManager: LocationManager
    var isGPSEnabled: Boolean? = null
    var isNetworkEnabled: Boolean? = null
    var lat: Double = 0.0
    var long: Double = 0.0
    lateinit var mapFragment: SupportMapFragment
    lateinit var map: GoogleMap
    private lateinit var linearLayoutManager: LinearLayoutManager
    var contact_uslist = ArrayList<Contact>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_contact_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseUI()

    }

    @SuppressLint("SetTextI18n")
    private fun initialiseUI() {
        mContext = requireContext()
        linearLayoutManager = LinearLayoutManager(mContext)
        mapFragment = childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment
        titleTextView = view?.findViewById(R.id.titleTextView) as TextView
        description = view?.findViewById(R.id.description) as TextView
        contactusRecycler = view?.findViewById(R.id.contactusRecycler) as RecyclerView
        progress = view?.findViewById(R.id.progress) as ProgressBar
        contactusRecycler.layoutManager = linearLayoutManager
        titleTextView.text = "Contact Us"

        if (CommonMethods.isInternetAvailable(mContext)) {
            fetchcurrentlatitudelongitude()
            getaboutusdata()

        } else {
            CommonMethods.showSuccessInternetAlert(mContext)
        }

    }

    private fun fetchcurrentlatitudelongitude() {
        var location: Location
        locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGPSEnabled!! && !isNetworkEnabled!!) {

        } else {
            if (isNetworkEnabled as Boolean) {
                if (ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                } else {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        0L,
                        0.0F,
                        this
                    )

                    location =
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    if (location != null) {
                        lat = location.latitude
                        long = location.longitude
                    }
                }
            }
            if (isGPSEnabled as Boolean) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0L,
                    0.0F,
                    this
                )
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (location != null) {
                    lat = location.latitude
                    long = location.longitude
                    println("lat---$lat")
                    println("lat---$long")
                    Log.e("CONTACTLATITUDE:", (lat + long).toString())
                }
            }
        }
        c_latitude = lat.toString()
        c_longitude = long.toString()
    }

    private fun getaboutusdata() {
        progress.visibility = View.VISIBLE
        val call: Call<Contactusresponse> = ApiClient.getClient.contact_us()
        call.enqueue(object : Callback<Contactusresponse> {
            override fun onFailure(call: Call<Contactusresponse>, t: Throwable) {
                progress.visibility = View.GONE

            }

            override fun onResponse(
                call: Call<Contactusresponse>,
                response: Response<Contactusresponse>
            ) {
                progress.visibility = View.GONE
                if (response.body()!!.status == 100) {
                    val descriptiontext = response.body()!!.contactusdata.description
                    latitude = response.body()!!.contactusdata.latitude
                    longitude = response.body()!!.contactusdata.longitude
                    description.text = descriptiontext
                    contact_uslist.addAll(response.body()!!.contactusdata.contacts)

                    if (contact_uslist.size > 0) {
                        contactusRecycler.visibility = View.VISIBLE
                    } else {
                        contactusRecycler.visibility = View.GONE
                        CommonMethods.showDialogueWithOk(mContext, "No Data Available.", "Alert")

                    }
                    val contact_usadapter = ContactUsAdapter(contact_uslist)
                    contactusRecycler.adapter = contact_usadapter

                } else {
                    if (response.body()!!.status == 101) {
                        CommonMethods.showDialogueWithOk(mContext, "Some error occured", "Alert")
                    }
                }
                mapFragment.getMapAsync { googleMap ->
                    Log.d("Map Working", "good")
                    map = googleMap
                    map.uiSettings.isMapToolbarEnabled = false
                    map.uiSettings.isZoomControlsEnabled = false
                    val latLng = LatLng(latitude.toDouble(), longitude.toDouble())
                    map.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .draggable(true)
                            .title("NAIS ALKHOR")
                    )


                    map.moveCamera(CameraUpdateFactory.newLatLng(latLng))

                    map.animateCamera(CameraUpdateFactory.zoomTo(13f))
                    map.setOnInfoWindowClickListener {

                        if (!isGPSEnabled!!) {
                            val callGPSSettingIntent = Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS
                            )
                            startActivity(callGPSSettingIntent)
                        } else {
                            val url =
                                "http://maps.google.com/maps?saddr=$c_latitude,$c_longitude&daddr=Nord Anglia International School Alkhor"

                            val intent = Intent(mContext, CustomwebviewMaps::class.java)
                            intent.putExtra("webview_url", url)
                            intent.putExtra("title", "NAISAK")
                            mContext.startActivity(intent)
                        }


                    }
                }

            }

        })
    }

    override fun onLocationChanged(location: Location?) {

    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }

    override fun onConnected(p0: Bundle?) {

    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onMarkerDragEnd(p0: Marker?) {
    }

    override fun onMarkerDragStart(p0: Marker?) {
    }

    override fun onMarkerDrag(p0: Marker?) {
    }

    override fun onMapLongClick(p0: LatLng?) {
    }

}