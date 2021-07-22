package com.nas.naisak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    /*****************************Created By Aparna 06 July 2021*********************************/
        /*First Loading splash activity inside com.nas.naisak.activity.splash.SplashActivity*/
    /*Splash*/
        /*Conditions and working of splash screen
        *
        * Delay the splash screen for 3 seconds after 3 seconds check is the app is launching first time or not
        * If the app is launching first time goto tutorial else check whether the app is login or not
        * If the app is Logged in then goto Home else go to Login Page
        * App Launch Check: Preference Manager : isFirstTimeLaunch
        * Logged in user check : Preference Manager : UserCode (if user code empty then the app is not logged in else the app is already logged in)*/

    /*Login*/
        /*Conditions and Working of login
        *
        * */



        /*************************************Notes**************************************************/
        /***************Created By Aparna 06 July 2021**********************/

        /*Handler(). postdelayed() is deprecated so use Handler(Looper.myLooper())
        * UI Blocking  :  UI Blocking is not recommending in android so we can block other user interaction during a process by setting a boolean value
        * if the process is going on set the boolean value to true on its finish set the  boolean value to false on every click check boolean value
        * if the boolean value is true do nothing if it is false go for the process*/
    }
}