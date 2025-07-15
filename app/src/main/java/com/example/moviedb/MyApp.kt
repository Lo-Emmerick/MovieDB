package com.example.moviedb

import android.app.Application
import com.example.moviedb.di.loadBusiness
import com.example.moviedb.di.loadRepositories
import com.example.moviedb.di.loadViewModels
import com.example.moviedb.di.movieApi
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            if (BuildConfig.DEBUG){
                androidLogger()
            }
            androidContext(this@MyApp)
            modules(movieApi, loadRepositories, loadViewModels, loadBusiness)
        }
    }
}