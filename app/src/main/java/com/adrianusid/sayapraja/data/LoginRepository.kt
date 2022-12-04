package com.adrianusid.sayapraja.data

import androidx.lifecycle.LiveData
import com.adrianusid.sayapraja.model.FirebaseClient
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LoginRepository {

    private val client = FirebaseClient()

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    val user = client.users
    val username: LiveData<String> = client.username
    val password: LiveData<String> = client.password
    val role: LiveData<String> = client.role
    val msg: LiveData<String> = client.msg

    fun login(userId: String) {

        executorService.execute {
            client.login(userId)

        }

    }

}