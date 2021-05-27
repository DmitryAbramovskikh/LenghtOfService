package com.dmabram15.lenghtofservice.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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

    private fun setGraphics() {
        window?.setBackgroundDrawableResource(R.drawable.ic_applicationbackground)
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
            .setCustomAnimations(
                FragmentTransaction.TRANSIT_ENTER_MASK,
                FragmentTransaction.TRANSIT_EXIT_MASK,
                FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
                FragmentTransaction.TRANSIT_FRAGMENT_CLOSE
            )
            .commitNow()
    }
}