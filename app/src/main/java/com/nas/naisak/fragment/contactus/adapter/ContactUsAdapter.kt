package com.nas.naisak.fragment.contactus.adapter

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.R
import com.nas.naisak.fragment.contactus.model.Contact

internal class ContactUsAdapter(private var contactlist: List<Contact>) :
    RecyclerView.Adapter<ContactUsAdapter.MyViewHolder>() {
    lateinit var context: Context


    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var number: TextView = view.findViewById(R.id.mobile)
        var email: TextView = view.findViewById(R.id.email)

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.contactusdetails, parent, false)
        context = parent.context


        return MyViewHolder(itemView)
    }

    private fun checkpermission() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    Manifest.permission.CALL_PHONE
                ),
                123
            )
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = contactlist[position]
        when {
            list.email.isEmpty() -> {
                holder.email.visibility = View.GONE
            }
            list.phone.isEmpty() -> {
                holder.number.visibility = View.GONE
            }
            list.name.isEmpty() -> {
                holder.title.visibility = View.GONE
            }


        }
        holder.title.text = list.name
        holder.number.text = list.phone

        val emailtext = list.email
        val content = SpannableString(emailtext)
        content.setSpan(UnderlineSpan(), 0, emailtext.length, 0)
        holder.email.text = content

        holder.number.setOnClickListener {
            checkpermission()
            val dialIntent = Intent(Intent.ACTION_CALL)
            dialIntent.data = Uri.parse("tel:" + list.phone)
            context.startActivity(dialIntent)
        }

        holder.email.setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_SEND_MULTIPLE
            )
            val deliveryAddress =
                arrayOf(holder.email.text.toString())
            emailIntent.putExtra(Intent.EXTRA_EMAIL, deliveryAddress)
            emailIntent.type = "text/plain"
            emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            val pm: PackageManager = it.context.packageManager
            val activityList = pm.queryIntentActivities(
                emailIntent, 0
            )
            for (app in activityList) {
                if (app.activityInfo.name.contains("com.google.android.gm")) {
                    val activity = app.activityInfo
                    val name = ComponentName(
                        activity.applicationInfo.packageName, activity.name
                    )
                    emailIntent.addCategory(Intent.CATEGORY_LAUNCHER)
                    emailIntent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK
                            or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                    emailIntent.component = name
                    it.context.startActivity(emailIntent)
                    break
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return contactlist.size
    }
}