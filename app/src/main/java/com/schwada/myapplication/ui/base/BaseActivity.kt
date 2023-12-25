package com.schwada.myapplication.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
        initViews()
    }

    // Subclasses must provide the layout resource ID
    abstract fun getLayoutResourceId(): Int

    // Subclasses can override this method to perform additional view initialization
    open fun initViews() {
        // Optional: Perform common view initialization here
    }
}
