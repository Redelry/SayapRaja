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
    private var editor : SharedPreferences.Editor ? = null
    private var sharedPreferences : SharedPreferences ?= null


    fun setId(id:String) = repo.setId(id)
    fun getId() : LiveData<String> = repo.getId()

    fun saveLogin(login: Boolean) = repo.saveLogin(login)

    fun getLogin() : LiveData<Boolean> = repo.getLogin()

//    fun getUserByID(userId : String) {
//        loginViewModel.role.observe(this) { role ->
//            if (role == "user") {
//                saveLogin(true)
//                sharedPreferences?.edit(commit = true) {
//                    putString("userId",userId)
//                }
//                startActivity(
//                    Intent(
//                        this,
//                        HomeApplicantActivity::class.java
//                    )
//                )
//                finish()
//            }
//        }
//    }


}