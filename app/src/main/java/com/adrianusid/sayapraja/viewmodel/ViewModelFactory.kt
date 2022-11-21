package com.adrianusid.sayapraja.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory private constructor(private val application: Application) : ViewModelProvider.NewInstanceFactory(){


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserPrefViewModel::class.java)){
            return UserPrefViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }


    companion object {
        @Volatile
        private var INSTANCE : ViewModelFactory ?=null
        @JvmStatic
        fun getInstance (application: Application) : ViewModelFactory {
            return INSTANCE ?: synchronized(this){
                val instance = ViewModelFactory(application)
                INSTANCE = instance
                return instance
            }
        }

    }
}