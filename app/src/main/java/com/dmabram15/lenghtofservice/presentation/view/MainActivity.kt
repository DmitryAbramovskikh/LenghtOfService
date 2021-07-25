package com.dmabram15.lenghtofservice.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dmabram15.lenghtofservice.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setGraphics()
    }

    private fun setGraphics() {
        window?.setBackgroundDrawableResource(R.drawable.ic_applicationbackground)
    }
}