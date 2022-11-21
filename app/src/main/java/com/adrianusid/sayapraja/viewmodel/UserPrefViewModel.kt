package com.adrianusid.sayapraja.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adrianusid.sayapraja.data.UserPreferencesRepository

class UserPrefViewModel(application: Application) : ViewModel() {

    private val repo: UserPreferencesRepository = UserPreferencesRepository(application)



    fun setId(id:String) = repo.setId(id)

    fun getId() : LiveData<String> = repo.getId()


}