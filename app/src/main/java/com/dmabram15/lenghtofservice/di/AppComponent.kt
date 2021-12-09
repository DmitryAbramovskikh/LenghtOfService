package com.dmabram15.lenghtofservice.di

import android.content.Context
import com.dmabram15.lenghtofservice.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component(modules = [AndroidInjectionModule::class])
interface AppComponent: AndroidInjector<App> {
    @Component.Builder
    interface Builder{

        @BindsInstance
        fun withContext(context: Context): Builder

        fun build(): AppComponent
    }
}