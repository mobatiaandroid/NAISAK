package com.nas.naisak.constants

import androidx.multidex.MultiDexApplication

class AppController : MultiDexApplication(){
    companion object{
        var mTitles: String? = null
        lateinit var mInstance:AppController
    }
    override fun onCreate() {
        super.onCreate()
        mInstance=this

    }
    init {
        mInstance = this
    }

    @Synchronized
    fun getInstance(): AppController? {
        return mInstance
    }
}