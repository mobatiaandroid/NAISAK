package com.nas.naisak.constants

import com.nas.naisak.activity.payment.payhere.model.PaymentDetailApiModel
import com.nas.naisak.activity.payment.payhere.model.PaymentDetailResponseModel
import com.nas.naisak.activtiy.communication.model.SocialMediaResponse
import com.nas.naisak.activtiy.login.model.LoginResponse
import com.nas.naisak.activtiy.notification.model.MessageDetailResponse
import com.nas.naisak.activtiy.notification.model.NotificationDetailApiModel
import com.nas.naisak.activtiy.payment.payhere.model.PaymentApiModel
import com.nas.naisak.activtiy.payment.payhere.model.PaymentGatewayApiModel
import com.nas.naisak.activtiy.payment.payhere.model.PaymentGatewayResponseModel
import com.nas.naisak.commonmodels.CommonDetailResponse
import com.nas.naisak.commonmodels.ModelWithPageNumberOnly
import com.nas.naisak.commonmodels.StudentListModel
import com.nas.naisak.fragment.aboutus.model.NAEResponseModel
import com.nas.naisak.fragment.calendar.model.CalendarResponseModel
import com.nas.naisak.fragment.calendar.model.TermCalendarResponseModel
import com.nas.naisak.fragment.communications.model.CommunicationResponseModel
import com.nas.naisak.fragment.contactus.model.Contactusresponse
import com.nas.naisak.fragment.home.model.Bannerresponse
import com.nas.naisak.fragment.home.model.HomeBadgeResponse
import com.nas.naisak.fragment.home.model.LogoutResponseModel
import com.nas.naisak.fragment.notification.model.NotificationResponseModel
import com.nas.naisak.fragment.parentsessentials.Model.ParentsEssentialResponseModel
import com.nas.naisak.fragment.payment.model.PaymentBannerResponse
import com.nas.naisak.fragment.payment.model.PaymentResponseModel
import com.nas.naisak.fragment.settings.model.ChangePasswordApiModel
import com.nas.naisak.fragment.settings.model.TermsOfServiceModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    /*************FORGOT PASSWORD****************/
    @POST("api/v1/parent/auth/forgotpassword")
    @FormUrlEncoded
    fun forgotpassword(
        @Field("email") email: String
    ): Call<ResponseBody>

    /*************SIGNUP****************/
    @POST("api/v1/parent/auth/register")
    @FormUrlEncoded
    fun registeruser(
        @Field("email") email: String
    ): Call<ResponseBody>

    /*************LOGIN****************/
    @POST("api/v1/parent/auth/login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("devicetype") devicetype: Int,
        @Field("deviceid") fcmid: String,
        @Field("device_identifier") deviceid: String
    ): Call<LoginResponse>

    /*************Notification List****************/
    @POST("api/v1/parent/notifications")
    @Headers("Content-Type: application/json")
    fun notificationList(
        @Body notificationList: ModelWithPageNumberOnly,
        @Header("Authorization") token:String
    ): Call<NotificationResponseModel>

    /*************NOTIFICATION DETAIL****************/
    @POST("api/v1/parent/notification/detail")
    @Headers("Content-Type: application/json")
    fun notificationdetail(
        @Body detaiApi: NotificationDetailApiModel,
        @Header("Authorization") token:String
    ): Call<MessageDetailResponse>

    /*************CONTACT US****************/
    @GET("api/v1/parent/contact_us")
    fun contact_us(): Call<Contactusresponse>

    /*************PAYMENT BANNER****************/
    @GET("api/v1/parent/payment/banner")
    @Headers("Content-Type: application/json")
    fun paymentBanner(
        @Header("Authorization") token:String
    ): Call<PaymentBannerResponse>

    /*************PAYMENT List****************/
    @POST("api/v1/parent/payment_categories")
    @Headers("Content-Type: application/json")
    fun paymentlist(
        @Body reportListModel: PaymentApiModel,
        @Header("Authorization") token:String
    ): Call<PaymentResponseModel>

    /*************PAYMENT DETAIL****************/
    @POST("api/v1/parent/payment_category/detail")
    @Headers("Content-Type: application/json")
    fun paymentdetail(
        @Body paymentid: PaymentDetailApiModel,
        @Header("Authorization") token:String
    ): Call<PaymentDetailResponseModel>

    /*************PAYMENT GATEWAY****************/
    @POST("api/v1/payment/getpaymentlink")
    @Headers("Content-Type: application/json")
    fun paymentGateway(
        @Body paymentid: PaymentGatewayApiModel,
        @Header("Authorization") token:String
    ): Call<PaymentGatewayResponseModel>

    /************PAYMENT INFORMATION****************/
    @POST("api/v1/parent/payment_informations")
    @Headers("Content-Type: application/json")
    fun paymentInformation(
        @Body notificationList: ModelWithPageNumberOnly,
        @Header("Authorization") token:String
    ):Call<CommonDetailResponse>

    /*************STUDENT_LIST****************/
    @GET("api/v1/parent/studentlist")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun studentList(
        @Header("Authorization") token:String
    ): Call<StudentListModel>


    /*************NAE  DETAILS****************/
    @GET("api/v1/parent/nordangliaeducation/get/nordangliaeducation")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun naeDetails(
        @Header("Authorization") token:String
    ): Call<NAEResponseModel>

    /*************NAE  DETAILS****************/
    @GET("api/v1/parent/term_calendar")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun termCalendar(
        @Header("Authorization") token:String
    ): Call<TermCalendarResponseModel>

    /*************NAE  DETAILS****************/
    @GET("api/v1/parent/parentessential/get/parent_essentials")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun parentessentials(
        @Header("Authorization") token:String
    ): Call<ParentsEssentialResponseModel>

  /*************COMMUNICATION****************/
    @GET("api/v1/parent/communication/get/communications")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun communication(
        @Header("Authorization") token:String
    ): Call<CommunicationResponseModel>
    /*************COMMUNICATION****************/
    @GET("api/v1/parent/communication/get/socialmedia")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun socialmedia(
        @Header("Authorization") token:String
    ): Call<SocialMediaResponse>

    /************PAYMENT INFORMATION****************/
    @POST("api/v1/parent/auth/logout")
    @Headers("Content-Type: application/json")
    fun logout(
        @Header("Authorization") token:String
    ):Call<LogoutResponseModel>

    /*************CHANGE PASSWORD****************/
    @POST("api/v1/parent/auth/changepassword")
    @Headers("Content-Type: application/json")
    fun changePassword(
        @Body  changePassword: ChangePasswordApiModel,
        @Header("Authorization") token:String
    ): Call<LogoutResponseModel>

    /*************TERMS OF SERVICES****************/
    @GET("api/v1/parent/terms_of_services")
    fun termsofservice(): Call<TermsOfServiceModel>

    /*************HOME BANNER****************/

    @GET("api/v1/parent/home_banner_images")
    fun bannerimages(): Call<Bannerresponse>


    /*************HOME BADGE****************/
    @GET("api/v1/parent/badge_counts")
    @Headers("Content-Type: application/json")
    fun homebadge(
        @Header("Authorization") token:String
    ): Call<HomeBadgeResponse>

    /*************CALENDAR****************/
    @GET("api/v1/parent/calendar")
    @Headers("Content-Type: application/json")
    fun calendar(
        @Header("Authorization") token:String
    ): Call<CalendarResponseModel>
}