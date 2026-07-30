package com.example.bok

import android.app.Application
import com.example.bok.data.AppContainer
import com.example.bok.data.AppDataContainer

class BookApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate(){
        super.onCreate()
        container = AppDataContainer(this)
    }
}