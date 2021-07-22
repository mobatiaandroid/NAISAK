package com.nas.naisak.activtiy.payment.information

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.R
import com.nas.naisak.activity.common_model.DetailListitems
import com.nas.naisak.activtiy.home.HomeActivity
import com.nas.naisak.activtiy.payment.payhere.adapter.PaymentInformationListAdapter
import com.nas.naisak.commonmodels.CommonDetailResponse
import com.nas.naisak.commonmodels.ModelWithPageNumberOnly
import com.nas.naisak.constants.*
import com.nas.naisak.constants.recyclermanager.OnItemClickListener
import com.nas.naisak.constants.recyclermanager.addOnItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentInformationActivity : AppCompatActivity() {
    lateinit var mContext: Context
    var id: String = ""
    var title: String = ""
    lateinit var titleTextView: TextView
    lateinit var primaryRecyclerdetails: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var progress: ProgressBar
    lateinit var back: ImageView
    lateinit var logoclick: ImageView
    var ibdetaillist = ArrayList<DetailListitems>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_detail)
        InitUI()
        back.setOnClickListener {
            finish()
        }

    }


    private fun InitUI() {
        mContext = this
        linearLayoutManager = LinearLayoutManager(mContext)
        titleTextView = findViewById(R.id.titleTextView)
        back = findViewById(R.id.back)
        logoclick = findViewById(R.id.logoclick)
        primaryRecyclerdetails = findViewById(R.id.primaryRecyclerdetails)
        progress = findViewById(R.id.progress)
        primaryRecyclerdetails.layoutManager = linearLayoutManager
        titleTextView.text = title

        if (CommonMethods.isInternetAvailable(mContext)) {
            ibdetailslist()
        } else {
            CommonMethods.showSuccessInternetAlert(mContext)
        }
        logoclick.setOnClickListener {
            val mIntent = Intent(mContext, HomeActivity::class.java)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            startActivity(mIntent)
        }

        primaryRecyclerdetails.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val urltype = ibdetaillist[position].url
                if (urltype.contains("pdf")) {
                    val intent = Intent(mContext, PdfReaderActivity::class.java)
                    intent.putExtra("pdf_url", ibdetaillist[position].url)
                    intent.putExtra("pdf_title", ibdetaillist[position].title)
                    startActivity(intent)
                } else {
                    val intent = Intent(mContext, WebviewLoader::class.java)
                    intent.putExtra("webview_url", ibdetaillist[position].url)
                    startActivity(intent)
                }

            }

        })
    }

    private fun ibdetailslist() {
        ibdetaillist = ArrayList()
        progress.visibility = View.VISIBLE
        var page=ModelWithPageNumberOnly("1")
        val call: Call<CommonDetailResponse> = ApiClient.getClient.paymentInformation(page,"Bearer "+ PreferenceManager.getUserCode(mContext))
        call.enqueue(object : Callback<CommonDetailResponse> {
            override fun onFailure(call: Call<CommonDetailResponse>, t: Throwable) {
                progress.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<CommonDetailResponse>,
                response: Response<CommonDetailResponse>
            ) {
                progress.visibility = View.GONE
                if (response.body()!!.status == 100) {
                    ibdetaillist.addAll(response.body()!!.data.detaillists)
                    val ib_detailsadapter = PaymentInformationListAdapter(ibdetaillist)
                    primaryRecyclerdetails.adapter = ib_detailsadapter
                }
                else {
                    if (response.body()!!.status == 101) {
                        CommonMethods.showDialogueWithOk(mContext, "Some error occured", "Alert")
                    }
                }
            }

        })
    }
}