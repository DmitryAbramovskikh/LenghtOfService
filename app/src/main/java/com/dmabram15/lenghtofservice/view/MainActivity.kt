package com.dmabram15.lenghtofservice.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dmabram15.lenghtofservice.R
import com.dmabram15.lenghtofservice.viewModel.SharedViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            showFragment()
        }
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