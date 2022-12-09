package com.adrianusid.sayapraja.data

import androidx.lifecycle.LiveData
import com.adrianusid.sayapraja.model.FirebaseClient
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository {
    private val client = FirebaseClient()

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    val userName : LiveData<String> = client.userName
    val userPhone : LiveData<String> = client.userPhone
    val msg : LiveData<String> = client.msg
    fun getUserName( id: String){
        executorService.execute {
            client.getUserName(id)
        }
    }
    fun getPhoneUser( id: String){
        executorService.execute {
            client.getUserPhone(id)
        }
    }
}