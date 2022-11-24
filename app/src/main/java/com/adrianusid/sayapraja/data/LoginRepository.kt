package com.adrianusid.sayapraja.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.adrianusid.sayapraja.model.FirebaseClient
import com.adrianusid.sayapraja.model.LoginModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LoginRepository {

    private val client = FirebaseClient()

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    val username : LiveData<String> = client.username
    val password : LiveData<String> = client.password
    val role : LiveData<String> = client.role
    val msg : LiveData<String> = client.msg

    fun login(loginModel: LoginModel,id: String){

        executorService.execute {
            client.login(loginModel,id)

        }

    }

}