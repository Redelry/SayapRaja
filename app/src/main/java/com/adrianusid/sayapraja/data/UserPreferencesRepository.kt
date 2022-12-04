package com.adrianusid.sayapraja.data

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserPreferencesRepository(application: Application) {

    private val pref: UserPreferences = UserPreferences(application)

    private val  executorService : ExecutorService = Executors.newSingleThreadExecutor()

    fun setId(id:String) {
        executorService.execute {
            pref.setId(id)
        }
    }

    fun getId() : LiveData<String> = pref.getId()

    fun saveLogin(login : Boolean){
        executorService.execute {
            pref.saveLogin(login)
        }
    }

    fun getLogin(): LiveData<Boolean> = pref.getLogin()


}