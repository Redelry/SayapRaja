package com.adrianusid.sayapraja.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adrianusid.sayapraja.data.CorpRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class CorpViewModel : ViewModel(){
    private val repo: CorpRepository = CorpRepository()
    val corpName : LiveData<String> = repo.corpName
    val corpPhone : LiveData<String> = repo.corpPhone
    private val auth = FirebaseAuth.getInstance()
    val user : FirebaseUser? = auth.currentUser
    val userId = user?.uid ?: ""
    val msg : LiveData<String> = repo.msg
    fun getCorpName( id: String){
        repo.getCorpName(id)
    }

    fun getCorpPhone( id: String){
        repo.getPhoneCorp(id)
    }


}