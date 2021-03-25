package com.dmabram15.lenghtofservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dmabram15.lenghtofservice.ui.main.LenghtOfServiceFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LenghtOfServiceFragment.newInstance())
                    .commitNow()
        }
    }
}