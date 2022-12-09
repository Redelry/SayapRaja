package com.adrianusid.sayapraja.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adrianusid.sayapraja.data.JobRepository
import com.adrianusid.sayapraja.model.ApllicantModel
import com.adrianusid.sayapraja.model.ListJobModel

class JobViewModel : ViewModel() {
    private val repository: JobRepository = JobRepository()


    val message: LiveData<String> = repository.message
    fun data(): LiveData<List<ListJobModel>> = repository.data()

    fun getData(idJob: String,idCorp: String) {
        repository.getData(idJob,idCorp)
    }

    fun getDataJobUser(idJob: String){
        repository.getDataJobUser(idJob)
    }


    fun searchListJob(positionJob: String,idJob: String){
        repository.searchListJob(positionJob,idJob)
    }
    fun editJob(
        positionJob: String,
        description: String,
        requirement: String,
        date: String,
        idCorp: String,

    ) {
        repository.editJob(positionJob, description, requirement, date, idCorp)
    }

    val isSuccess: LiveData<Boolean> = repository.isSuccess
    fun deleteJob(idJob: String) {
        repository.deleteJob(idJob)
    }
}