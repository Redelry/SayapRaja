package com.adrianusid.sayapraja.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adrianusid.sayapraja.data.CorpPreferencesRepository
import com.adrianusid.sayapraja.data.UserPreferencesRepository

class CorpPrefViewModel(application: Application) : ViewModel() {

    private val repo: CorpPreferencesRepository = CorpPreferencesRepository(application)



    fun setId(id:String) = repo.setId(id)

    fun getId() : LiveData<String> = repo.getId()

    fun saveLogin(login: Boolean) = repo.saveLogin(login)

    fun getLogin() : LiveData<Boolean> = repo.getLogin()

}