package com.adrianusid.sayapraja.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adrianusid.sayapraja.data.AddJobRepository
import com.adrianusid.sayapraja.model.AddJobModel

class AddJobViewModel : ViewModel() {
    private val repo: AddJobRepository = AddJobRepository()
    val isSuccess : LiveData<Boolean> = repo.isSuccess

    fun addJob(addJobModel: AddJobModel, context: Context){
        repo.addJob(addJobModel,context)
    }

    fun getJobId() = repo.getJobId()


}