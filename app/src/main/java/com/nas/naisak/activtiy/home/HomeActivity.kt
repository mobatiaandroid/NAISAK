package com.nas.naisak.activtiy.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.R
import com.nas.naisak.activtiy.home.adapter.HomeListAdapter
import com.nas.naisak.constants.*
import com.nas.naisak.fragment.aboutus.NordAngliaEductaionFragment
import com.nas.naisak.fragment.calendar.CalendarFragment
import com.nas.naisak.fragment.communications.CommunicationFragment
import com.nas.naisak.fragment.contactus.ContactUsFragment
import com.nas.naisak.fragment.home.HomeScreenFragment
import com.nas.naisak.fragment.home.mContext
import com.nas.naisak.fragment.notification.NotificationFragment
import com.nas.naisak.fragment.parentsessentials.ParentsEssentialsFragment
import com.nas.naisak.fragment.payment.PaymentFragment
import com.nas.naisak.fragment.settings.SettingsFragment
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity(), AdapterView.OnItemLongClickListener {

    val manager = supportFragmentManager
    lateinit var navigation_menu: ImageView
    lateinit var settings_icon: ImageView
    lateinit var shadowBuilder: MyDragShadowBuilder
    lateinit var context: Context
    lateinit var clipData: ClipData
    lateinit var mListItemArray: Array<String>
    var mListImgArray: TypedArray? = null
    lateinit var linear_layout: LinearLayout
    lateinit var drawer_layout: DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var logoClickImgView: ImageView
    lateinit var homelist: ListView
    lateinit var homeprogress:ProgressBar
    var mFragment: Fragment? = null
    var sPosition: Int = 0
    var previousTriggerTypeNew: Int = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        initializeUI()
        showfragmenthome()
    }

    fun showfragmenthome() {
        val transaction = manager.beginTransaction()
        val fragment = HomeScreenFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    @SuppressLint("Recycle", "WrongViewCast")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun initializeUI() {

        context = this
        homelist = findViewById<ListView>(R.id.homelistview)
        drawer_layout = findViewById(R.id.drawer_layout)
        linear_layout = findViewById(R.id.linear_layout)
        homeprogress = findViewById(R.id.homeprogress)
        var downarrow = findViewById<ImageView>(R.id.downarrow)

        mListItemArray =
            applicationContext.resources.getStringArray(R.array.navigation_item_reg)
        mListImgArray =
            applicationContext.resources.obtainTypedArray(R.array.navigation_item_reg_icons)


        val width = (resources.displayMetrics.widthPixels / 1.7).toInt()
        val params = linear_layout
            .layoutParams as DrawerLayout.LayoutParams
        params.width = width
        linear_layout.layoutParams = params
        val myListAdapter = HomeListAdapter(this, mListItemArray, mListImgArray!!)
        homelist.adapter = myListAdapter
        homelist.onItemLongClickListener = this


        homelist.setOnItemClickListener { adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            if (PreferenceManager.getUserCode(context).equals("")) {
                if (position == 0) {
                    mFragment = HomeScreenFragment()
                    replaceFragmentsSelected(position)
                }
                else if (position == 1) {
                    CommonMethods.showDialogueWithOk(context,"This Feature is only available for registered users","Alert")
                }
                else if (position == 2) {
                    CommonMethods.showDialogueWithOk(context,"This Feature is only available for registered users","Alert")
                }
                else if (position == 3 )
                    {
                        CommonMethods.showDialogueWithOk(context,"This Feature is only available for registered users","Alert")
                }
                else if (position == 4) {
                    CommonMethods.showDialogueWithOk(context,"This Feature is only available for registered users","Alert")
                }
                else if (position == 5) {
                    CommonMethods.showDialogueWithOk(context,"This Feature is only available for registered users","Alert")
                }
                else if (position == 6) {
                    CommonMethods.showDialogueWithOk(context,"This Feature is only available for registered users","Alert")
                }
                else if (position == 7) {
                    mFragment = ContactUsFragment()
                    replaceFragmentsSelected(position)
                }else if (position == 8) {
                    mFragment = SettingsFragment()
                    replaceFragmentsSelected(position)
                }
            } else {
                if (position == 0) {
                    mFragment = HomeScreenFragment()
                    replaceFragmentsSelected(position)
                }
                else if (position == 1) {
                    mFragment = CalendarFragment()
                    replaceFragmentsSelected(position)
                }
                else if (position == 2) {
                    mFragment = NotificationFragment()
                    replaceFragmentsSelected(position)
                }
                else if (position == 3 )
                {
                    mFragment = CommunicationFragment()
                    replaceFragmentsSelected(position)
                }
                else if (position == 4) {
                    mFragment = PaymentFragment()
                    replaceFragmentsSelected(position)
                }
                else if (position == 5) {
                    mFragment = ParentsEssentialsFragment()
                    replaceFragmentsSelected(position)
                }
                else if (position == 6) {
                    mFragment = NordAngliaEductaionFragment()
                    replaceFragmentsSelected(position)
                }
                else if (position == 7) {
                    mFragment = ContactUsFragment()
                    replaceFragmentsSelected(position)
                }else if (position == 8) {
                    mFragment = SettingsFragment()
                    replaceFragmentsSelected(position)
                }
            }

        }

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_titlebar)
        supportActionBar!!.elevation = 0F

        var view = supportActionBar!!.customView
        toolbar = view.parent as Toolbar
        toolbar.setBackgroundColor(resources.getColor(R.color.white))
        toolbar.setContentInsetsAbsolute(0, 0)

        navigation_menu = view.findViewById(R.id.action_bar_back)
        settings_icon = view.findViewById(R.id.action_bar_forward)
        logoClickImgView = view.findViewById(R.id.logoClickImgView)
        settings_icon.visibility = View.INVISIBLE
        homelist.setBackgroundColor(getColor(R.color.split_bg))
        homelist.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {}
            override fun onScroll(
                view: AbsListView,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                if (view.id == homelist.id) {
                    val currentFirstVisibleItem: Int = homelist.lastVisiblePosition

                    if (currentFirstVisibleItem == totalItemCount - 1) {
                        downarrow.visibility = View.INVISIBLE
                    } else {
                        downarrow.visibility = View.VISIBLE
                    }
                }
            }
        })
        mListItemArray = context.resources.getStringArray(R.array.navigation_item_reg)
        mListImgArray = context.resources.obtainTypedArray(R.array.navigation_item_reg_icons)
        navigation_menu.setOnClickListener {
            if (drawer_layout.isDrawerOpen(linear_layout)) {
                drawer_layout.closeDrawer(linear_layout)
            } else {
                drawer_layout.openDrawer(linear_layout)
            }
        }

        logoClickImgView.setOnClickListener(View.OnClickListener {
            settings_icon.visibility = View.INVISIBLE
            if (drawer_layout.isDrawerOpen(linear_layout)) {
                drawer_layout.closeDrawer(linear_layout)
            }
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            mFragment = HomeScreenFragment()
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        })

        settings_icon.setOnClickListener {
            val fm = supportFragmentManager
            settings_icon.visibility = View.INVISIBLE
            val currentFragment =
                fm.findFragmentById(R.id.fragment_holder)
            if (drawer_layout.isDrawerOpen(linear_layout)) {
                drawer_layout.closeDrawer(linear_layout)
            }
            mFragment = SettingsFragment()
            if (mFragment != null) {
                val fragmentManager =
                    supportFragmentManager
                fragmentManager.beginTransaction()
                    .add(R.id.fragment_holder, mFragment!!, "Settings")
                    .addToBackStack("Settings").commit()

                supportActionBar!!.setTitle(R.string.null_value)
                settings_icon.visibility = View.INVISIBLE

            }
        }

    }


    override fun onItemLongClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ): Boolean {

        shadowBuilder = MyDragShadowBuilder(view)
        sPosition = position
        val selecteditem = parent?.getItemIdAtPosition(position)
        view?.setBackgroundColor(Color.parseColor("#47C2D1"))
        val data = ClipData.newPlainText("", "")
        view?.startDrag(data, shadowBuilder, view, 0)
        view!!.visibility = View.VISIBLE
        drawer_layout.closeDrawer(linear_layout)
        return false
    }

    private fun replaceFragmentsSelected(position: Int) {
        settings_icon.visibility = View.INVISIBLE
        if (mFragment != null) {
            val fragmentManager = supportFragmentManager
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_holder, mFragment!!,
                    mListItemArray[position]
                )
                .addToBackStack(mListItemArray[position]).commitAllowingStateLoss()
            homelist.setItemChecked(position, true)
            homelist.setSelection(position)
            supportActionBar!!.setTitle(R.string.null_value)
            if (drawer_layout.isDrawerOpen(linear_layout)) {
                drawer_layout.closeDrawer(linear_layout)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (drawer_layout.isDrawerOpen(linear_layout)) {
            drawer_layout.closeDrawer(linear_layout)
        }
        settings_icon.visibility = View.INVISIBLE

    }

    fun fragmentIntent(mFragment: Fragment?) {
        if (mFragment != null) {
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .add(R.id.fragment_holder, mFragment, AppController.mTitles)
                .addToBackStack(AppController.mTitles).commitAllowingStateLoss() //commit
        }
    }


    private fun checkpermission() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CALL_PHONE
                ),
                123
            )
        }
    }


    override fun onResume() {
        super.onResume()
        Intent.FLAG_ACTIVITY_CLEAR_TASK

    }





}
