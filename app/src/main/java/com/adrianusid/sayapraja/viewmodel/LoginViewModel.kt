package com.adrianusid.sayapraja.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adrianusid.sayapraja.data.LoginRepository
import com.adrianusid.sayapraja.model.LoginModel

class LoginViewModel : ViewModel(){


    private val repo: LoginRepository = LoginRepository()
    val username : LiveData<String> = repo.username
    val password : LiveData<String> = repo.password
    val role : LiveData<String> = repo.role
    val msg : LiveData<String> = repo.msg
    val user = repo.user

    fun login(userId: String){
        repo.login(userId)
    }
}