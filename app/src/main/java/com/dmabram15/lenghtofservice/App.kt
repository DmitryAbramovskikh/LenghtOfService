package com.dmabram15.lenghtofservice

import android.app.Application
import androidx.room.Room
import com.dmabram15.lenghtofservice.model.repository.PeriodsDatabase

class App : Application() {

    private lateinit var periodsDatabase: PeriodsDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this
        periodsDatabase = Room.databaseBuilder(
            applicationContext,
            PeriodsDatabase::class.java,
            "periodsDB"
        ).build()
    }

    fun getDb(): PeriodsDatabase = periodsDatabase

    companion object {
        private lateinit var instance : App

        fun getInstance(): App = instance
    }

}