package com.dmabram15.lenghtofservice.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dmabram15.lenghtofservice.R
import com.dmabram15.lenghtofservice.viewModel.SharedViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setGraphics()
    }

    override fun onPause() {
        ViewModelProvider(this).get(SharedViewModel::class.java).saveData()
        super.onPause()
    }

    private fun setGraphics() {
        window?.setBackgroundDrawableResource(R.drawable.ic_applicationbackground)
    }
}