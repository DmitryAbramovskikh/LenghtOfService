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

        if (savedInstanceState == null) {
            showFragment()
        }
    }

    override fun onPause() {
        ViewModelProvider(this).get(SharedViewModel::class.java).saveData()
        super.onPause()
    }

    private fun setGraphics() {
        window?.setBackgroundDrawableResource(R.drawable.ic_applicationbackground_new)
    }

    private fun showFragment() {
        val fragment = if (supportFragmentManager.backStackEntryCount == 0) {
            LengthOfServiceFragment.newInstance()
        } else {
            supportFragmentManager
                .fragments[supportFragmentManager.fragments.size]
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }
}