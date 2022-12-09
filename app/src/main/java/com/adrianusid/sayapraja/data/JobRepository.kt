package com.adrianusid.sayapraja.data

import androidx.lifecycle.LiveData
import com.adrianusid.sayapraja.model.ApllicantModel
import com.adrianusid.sayapraja.model.FirebaseClient
import com.adrianusid.sayapraja.model.ListJobModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class JobRepository {
    private val client = FirebaseClient()
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    val message: LiveData<String> = client.msg
    fun data(): LiveData<List<ListJobModel>> = client.data

    fun dataApli() : LiveData<List<ApllicantModel>> = client.dataAplicant


    fun getData(idJob: String,idCorp: String) {
        executorService.execute { client.getJobListCorp(idJob,idCorp) }
    }

    fun getDataJobUser(idJob: String){
        executorService.execute { client.getJobListUser(idJob) }
    }

    fun searchListJob(positionJob: String,idJob: String){
        executorService.execute { client.searchJob(positionJob,idJob)}
    }

    val isSuccess: LiveData<Boolean> = client.isSuccess

    fun editJob(positionJob: String, description: String, requirement: String,date: String,idJob: String){
        executorService.execute { client.editJob(positionJob,description, requirement,date,idJob) }
    }

    fun deleteJob(idJob: String){
        executorService.execute { client.deleteJob(idJob) }
    }
}