package com.nas.naisak.activity.payment.payhere.model

import com.google.gson.annotations.SerializedName

class PaymentDetailDataModel (
    @SerializedName("id") val id: Int=0,
    @SerializedName("is_paid") val is_paid: Int=0,
    @SerializedName("payment_id") val payment_id: Int=0,
    @SerializedName("payment_type") val payment_type: Int=0,
    @SerializedName("category_name") val category_name: String="",
    @SerializedName("invoice_description") val invoice_description: String="",
    @SerializedName("amount") val amount: String="",
    @SerializedName("formated_amount") val formated_amount: String="",
    @SerializedName("trn_no") val trn_no: String="",
    @SerializedName("paid_amount") val paid_amount: String="",
    @SerializedName("payment_date") val payment_date: String="",
    @SerializedName("order_id") val order_id: String="",
    @SerializedName("student_name") val student_name: String="",
    @SerializedName("isams_no") val isams_no: String="",
    @SerializedName("parent_name") val parent_name: String="",
    @SerializedName("invoice_note") val invoice_note: String="",
    @SerializedName("billing_code") val billing_code: String="",
    @SerializedName("remaining_amount") val remaining_amount: String="",
    @SerializedName("invoice_no") val invoice_no: String=""
)