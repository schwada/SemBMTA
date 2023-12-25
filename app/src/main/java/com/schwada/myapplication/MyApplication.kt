package com.schwada.myapplication

import android.app.Application
import android.content.Context

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate();
        val mycontext: Context = this;
    }
}