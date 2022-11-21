package com.adrianusid.sayapraja.data

import android.content.Context
import com.adrianusid.sayapraja.model.FirebaseClient
import com.adrianusid.sayapraja.model.UserModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RegisterRepository {
    private val client = FirebaseClient()

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()


    fun register(userModel: UserModel, context: Context){



        executorService.execute {
            client.register(userModel,context)
        }


    }

    fun getUserId() = client.id

}