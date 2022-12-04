package com.adrianusid.sayapraja.viewmodel

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adrianusid.sayapraja.HomeApplicantActivity
import com.adrianusid.sayapraja.data.UserPreferencesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserPrefViewModel(application: Application) : ViewModel() {

    private val repo: UserPreferencesRepository = UserPreferencesRepository(application)
    private val auth = FirebaseAuth.getInstance()
    val user : FirebaseUser? = auth.currentUser
    val userId = user?.uid ?: ""


    fun setId(id:String) = repo.setId(id)
    fun getId() : LiveData<String> = repo.getId()



    fun getLogin() : LiveData<Boolean> = repo.getLogin()




}