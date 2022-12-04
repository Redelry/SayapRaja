package com.adrianusid.sayapraja.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.adrianusid.sayapraja.model.FirebaseClient
import com.adrianusid.sayapraja.model.UserModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RegisterRepository {
    private val client = FirebaseClient()

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    val isSuccess: LiveData<Boolean> = client.isSuccess

    fun register(userModel: UserModel, userId: String, context: Context) {


        executorService.execute {
            client.register(userModel, userId, context)
        }


    }


}