package com.nas.naisak.activtiy.payment.payhere

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.naisak.R
import com.nas.naisak.activtiy.home.HomeActivity
import com.nas.naisak.activtiy.payment.payhere.adapter.PaymentListAdapter
import com.nas.naisak.activtiy.payment.payhere.model.PaymentApiModel
import com.nas.naisak.commonadapters.StudentListAdapter
import com.nas.naisak.commonmodels.StudentDataListResponse
import com.nas.naisak.commonmodels.StudentListModel
import com.nas.naisak.commonmodels.StudentListResponse
import com.nas.naisak.constants.ApiClient
import com.nas.naisak.constants.CommonMethods
import com.nas.naisak.constants.PreferenceManager
import com.nas.naisak.constants.recyclermanager.OnItemClickListener
import com.nas.naisak.constants.recyclermanager.addOnItemClickListener
import com.nas.naisak.fragment.payment.model.PaymentListModel
import com.nas.naisak.fragment.payment.model.PaymentResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class PaymentActivity : AppCompatActivity(){
    lateinit var mContext: Context
    private lateinit var relativeHeader: RelativeLayout
    private lateinit var backRelative: RelativeLayout
    private lateinit var logoClickImgView: ImageView
    private lateinit var btn_left: ImageView
    private lateinit var heading: TextView
    lateinit var studentNameTxt: TextView
    lateinit var imagicon: ImageView
    lateinit var studentSpinner: LinearLayout
    var studentListArrayList = ArrayList<StudentDataListResponse>()
    lateinit var studentName: String
    var studentId: Int=0
    lateinit var studentImg: String
    lateinit var studentClass: String
    lateinit var paymentListArrayList: ArrayList<PaymentListModel>
    lateinit var recycler_view_list: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        mContext=this
        relativeHeader = findViewById(R.id.relativeHeader)
        initUI()
        if (CommonMethods.isInternetAvailable(mContext)) {
            callStudentListApi()
        } else {
            CommonMethods.showSuccessInternetAlert(mContext)
        }
    }
    fun initUI() {
        relativeHeader = findViewById(R.id.relativeHeader)
        backRelative = findViewById(R.id.backRelative)
        heading = findViewById(R.id.heading)
        btn_left = findViewById(R.id.btn_left)
        logoClickImgView = findViewById(R.id.logoClickImgView)
        heading.setText("Absence")
        studentNameTxt=findViewById(R.id.studentName)
        imagicon=findViewById(R.id.imagicon)
        studentSpinner=findViewById(R.id.studentSpinner)

        recycler_view_list=findViewById(R.id.mAbsenceListView)
        linearLayoutManager = LinearLayoutManager(mContext)
        recycler_view_list.layoutManager = linearLayoutManager
        recycler_view_list.itemAnimator = DefaultItemAnimator()
        recycler_view_list.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val intent = Intent(mContext, PaymentDetailActivity::class.java)
                intent.putExtra("id", paymentListArrayList[position].id.toString())
                intent.putExtra("title",paymentListArrayList[position].title)
                startActivity(intent)
            }

        })

        btn_left.setOnClickListener(View.OnClickListener {
            finish()
        })
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        studentSpinner.setOnClickListener(View.OnClickListener {

            showStudentList(mContext,studentListArrayList)
        })

    }


    fun callStudentListApi() {
        val token = PreferenceManager.getUserCode(mContext)
        val call: Call<StudentListModel> = ApiClient.getClient.studentList("Bearer " + token)
        call.enqueue(object : Callback<StudentListModel> {
            override fun onFailure(call: Call<StudentListModel>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<StudentListModel>,
                response: Response<StudentListModel>
            ) {
                if (response.body()!!.status == 100)
                {
                    studentListArrayList.addAll(response.body()!!.dataArray.studentListArray)
                    if (PreferenceManager.getStudentID(mContext)==0)
                    {
                        Log.e("Empty Img", "Empty")
                        studentName = studentListArrayList.get(0).studentName
                        studentImg = studentListArrayList.get(0).photo
                        studentId = studentListArrayList.get(0).studentId
                        studentClass = studentListArrayList.get(0).section
                        PreferenceManager.setStudentID(mContext, studentId)
                        PreferenceManager.setStudentName(mContext, studentName)
                        PreferenceManager.setStudentPhoto(mContext, studentImg)
                        PreferenceManager.setStudentClass(mContext, studentClass)
                        studentNameTxt.text = studentName
                        if (!studentImg.equals("")) {
                            Glide.with(mContext) //1
                                .load(studentImg)
                                .placeholder(R.drawable.boy)
                                .error(R.drawable.boy)
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .transform(CircleCrop()) //4
                                .into(imagicon)
                        } else {
                            imagicon.setImageResource(R.drawable.boy)

                        }

                    } else {
                        studentName = PreferenceManager.getStudentName(mContext)!!
                        studentImg = PreferenceManager.getStudentPhoto(mContext)!!
                        studentId = PreferenceManager.getStudentID(mContext)!!
                        studentClass = PreferenceManager.getStudentClass(mContext)!!
                        studentNameTxt.text = studentName
                        if (studentImg != "") {
                            Glide.with(mContext) //1
                                .load(studentImg)
                                .placeholder(R.drawable.boy)
                                .error(R.drawable.boy)
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .transform(CircleCrop()) //4
                                .into(imagicon)
                        } else {
                            imagicon.setImageResource(R.drawable.boy)
                        }
                    }

                    if (CommonMethods.isInternetAvailable(mContext)) {
                        callCategoryList()
                    }
                    else
                    {
                        CommonMethods.showSuccessInternetAlert(mContext)
                    }
                }
                else
                {

                }
            }
        })
    }



    fun showStudentList(context: Context, mStudentList: ArrayList<StudentDataListResponse>) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_student_list)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var btn_dismiss = dialog.findViewById(R.id.btn_dismiss) as Button
        var studentListRecycler = dialog.findViewById(R.id.recycler_view_social_media) as RecyclerView
        iconImageView.setImageResource(R.drawable.boy)
        //if(mSocialMediaArray.get())
        val sdk = Build.VERSION.SDK_INT
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            btn_dismiss.setBackgroundDrawable(
                mContext.resources.getDrawable(R.drawable.button_new)
            )
        } else {
            btn_dismiss.background = mContext.resources.getDrawable(R.drawable.button_new)
        }

        studentListRecycler.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        studentListRecycler.layoutManager = llm
        val studentAdapter = StudentListAdapter(mContext,mStudentList)
        studentListRecycler.adapter = studentAdapter
        btn_dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }



        studentListRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                studentName = studentListArrayList[position].studentName
                studentImg = studentListArrayList[position].photo
                studentId = studentListArrayList[position].studentId
                studentClass = studentListArrayList[position].section
                PreferenceManager.setStudentID(mContext, studentId)
                PreferenceManager.setStudentName(mContext, studentName)
                PreferenceManager.setStudentPhoto(mContext, studentImg)
                PreferenceManager.setStudentClass(mContext, studentClass)
                studentNameTxt.text = studentName
                if (studentImg != "") {
                    Glide.with(mContext) //1
                        .load(studentImg)
                        .placeholder(R.drawable.boy)
                        .error(R.drawable.boy)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .transform(CircleCrop()) //4
                        .into(imagicon)
                } else {
                    imagicon.setImageResource(R.drawable.boy)
                }
                if (CommonMethods.isInternetAvailable(mContext)) {
                    callCategoryList()
                }
                else
                {
                    CommonMethods.showSuccessInternetAlert(mContext)
                }
                dialog.dismiss()
            }
        })
        dialog.show()
    }


    fun callCategoryList()
    {
        paymentListArrayList= ArrayList()
        val token = PreferenceManager.getUserCode(mContext)
        val studentid = PaymentApiModel(PreferenceManager.getStudentID(mContext)!!.toString())
        val call: Call<PaymentResponseModel> =
            ApiClient.getClient.paymentlist(studentid, "Bearer " + token)
        call.enqueue(object : Callback<PaymentResponseModel> {
            override fun onFailure(call: Call<PaymentResponseModel>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<PaymentResponseModel>,
                response: Response<PaymentResponseModel>
            ) {
                if (response.body()!!.status==100)
                {
                    if (response.body()!!.data.lists.size>0)
                    {
                        paymentListArrayList.addAll(response.body()!!.data.lists)
                        recycler_view_list.visibility= View.VISIBLE
                        val rAdapter: PaymentListAdapter = PaymentListAdapter(mContext,paymentListArrayList)
                        recycler_view_list.adapter = rAdapter
                    }
                    else
                    {
                        recycler_view_list.visibility= View.GONE
                        CommonMethods.showDialogueWithOk(mContext, "No Data Available.", "Alert")
                    }

                }
            }

        })
    }
}