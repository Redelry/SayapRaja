package com.adrianusid.sayapraja.data

import androidx.lifecycle.LiveData
import com.adrianusid.sayapraja.model.FirebaseClient
import com.adrianusid.sayapraja.model.ListJobModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class JobRepository {
    private val client = FirebaseClient()
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    val message: LiveData<String> = client.msg
    fun data(): LiveData<List<ListJobModel>> = client.data

    fun getData(idCorp : String) {
        executorService.execute { client.getJobListCorp(idCorp) }
    }

    val isSuccess: LiveData<Boolean> = client.isSuccess

    fun editJob(position: String, description: String, requirement: String, id: String){
        executorService.execute { client.editJob(position,description, requirement, id) }
    }

    fun deleteJob(id: String){
        executorService.execute { client.deleteJob(id) }
    }
}