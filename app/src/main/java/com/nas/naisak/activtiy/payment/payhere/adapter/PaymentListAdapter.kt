package com.nas.naisak.activtiy.payment.payhere.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.R
import com.nas.naisak.fragment.payment.model.PaymentListModel
import java.util.ArrayList

class PaymentListAdapter (private var mContext: Context, private var repoetDetailArray: ArrayList<PaymentListModel>) :
    RecyclerView.Adapter<PaymentListAdapter.MyViewHolder>() {
    lateinit var clickedurl:String

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var termname: TextView = view.findViewById(R.id.listTxtTitle)
        var status: TextView = view.findViewById(R.id.status)
        var statusLayout: RelativeLayout = view.findViewById(R.id.statusLayout)
//        var clickLinear: LinearLayout = view.findViewById(R.id.clickLinear)

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_payment_recycler, parent, false)
        return MyViewHolder(itemView)
    }


    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val summary = repoetDetailArray[position]
        holder.termname.text = repoetDetailArray[position].title
//        if (repoetDetailArray[position].status==0)
//        {
//            holder.statusLayout.visibility=View.VISIBLE
//            holder.status.text="New"
//            holder.statusLayout.setBackgroundResource(R.drawable.rectangle_red)
//
//        }else if (repoetDetailArray[position].status==1)
//        {
//            holder.statusLayout.visibility=View.GONE
//        }
//        else if (repoetDetailArray[position].status==2)
//        {
//            holder.statusLayout.visibility=View.VISIBLE
//            holder.status.text="Updated"
//            holder.statusLayout.setBackgroundResource(R.drawable.rectangle_blue_update)
//        }

//        holder.clickLinear.setOnClickListener {
//
//            clickedurl = repoetDetailArray[position].file
//            val intent = Intent(mContext, PdfReaderActivity::class.java)
//            intent.putExtra("pdf_url", repoetDetailArray[position].file)
//            intent.putExtra("pdf_title", repoetDetailArray[position].reporting_cycle)
//            mContext.startActivity(intent)
//
//            // mContext.startActivity(Intent(mContext, WebviewLoad::class.java).putExtra("Url",repoetDetailArray[position].file))
////            mContext.startActivity(Intent(mContext, PdfViewer::class.java).putExtra("Url",repoetDetailArray[position].file).putExtra("title",repoetDetailArray[position].report_cycle))
//
//        }
    }
    override fun getItemCount(): Int {

        return repoetDetailArray.size

    }
}