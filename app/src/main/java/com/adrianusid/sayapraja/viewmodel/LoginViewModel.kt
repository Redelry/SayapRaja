package com.adrianusid.sayapraja.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adrianusid.sayapraja.data.LoginRepository
import com.adrianusid.sayapraja.data.RegisterRepository
import com.adrianusid.sayapraja.model.FirebaseClient
import com.adrianusid.sayapraja.model.LoginModel
import com.adrianusid.sayapraja.model.UserModel

class LoginViewModel : ViewModel(){


    private val repo: LoginRepository = LoginRepository()
    val username : LiveData<String> = repo.username
    val password : LiveData<String> = repo.password
    val msg : LiveData<String> = repo.msg


    fun login(loginModel: LoginModel, id: String){
        repo.login(loginModel,id)
    }
}