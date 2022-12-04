package com.adrianusid.sayapraja.viewmodel

import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adrianusid.sayapraja.HomeApplicantActivity
import com.adrianusid.sayapraja.data.CorpNameRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class CorpNameViewModel : ViewModel(){
    private val repo: CorpNameRepository = CorpNameRepository()
    val corpName : LiveData<String> = repo.corpName
    private val auth = FirebaseAuth.getInstance()
    val user : FirebaseUser? = auth.currentUser
    val userId = user?.uid ?: ""
    val msg : LiveData<String> = repo.msg
    fun getCorpName( id: String){
        repo.getCorpName(id)
    }


}