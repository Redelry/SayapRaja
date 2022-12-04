package com.adrianusid.sayapraja.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.adrianusid.sayapraja.model.AddJobModel
import com.adrianusid.sayapraja.model.FirebaseClient
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AddJobRepository {

    private val client = FirebaseClient()

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    val isSuccess: LiveData<Boolean> = client.isSuccess


    fun addJob(addJobModel: AddJobModel, context: Context, idCorp: String) {


        executorService.execute {
            client.addJob(addJobModel, context, idCorp)
        }


    }


    fun getJobId() = client.idJob

}