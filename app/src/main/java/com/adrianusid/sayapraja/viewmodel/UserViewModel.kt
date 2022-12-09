package com.adrianusid.sayapraja.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adrianusid.sayapraja.data.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserViewModel : ViewModel(){
    private val repo: UserRepository = UserRepository()
    val userName : LiveData<String> = repo.userName
    private val auth = FirebaseAuth.getInstance()
    val user : FirebaseUser? = auth.currentUser
    val userId = user?.uid ?: ""
    val userPhone : LiveData<String> = repo.userPhone
    val msg : LiveData<String> = repo.msg
    fun getUserName( id: String){
        repo.getUserName(id)
    }
    fun getUserPhone( id: String){
        repo.getPhoneUser(id)
    }

}