package com.adrianusid.sayapraja.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adrianusid.sayapraja.data.JobRepository
import com.adrianusid.sayapraja.model.ListJobModel

class JobViewModel : ViewModel(){
    private val repository: JobRepository = JobRepository()



    val message: LiveData<String> = repository.message
    fun data(): LiveData<List<ListJobModel>> = repository.data()

    fun getData(idCorp : String) {
        repository.getData(idCorp)
    }

    fun editJob(position: String, description: String, requirement: String, id: String){
        repository.editJob(position, description, requirement, id)
    }
    val isSuccess: LiveData<Boolean> = repository.isSuccess
    fun deleteJob(id:String){
        repository.deleteJob(id)
    }
}