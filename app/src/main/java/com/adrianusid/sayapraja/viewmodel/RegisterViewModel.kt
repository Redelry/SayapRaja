package com.adrianusid.sayapraja.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adrianusid.sayapraja.data.RegisterRepository
import com.adrianusid.sayapraja.model.UserModel

class RegisterViewModel : ViewModel(){



    private val repo: RegisterRepository = RegisterRepository()
    val isSuccess : LiveData<Boolean> = repo.isSuccess
    fun register(userModel: UserModel, userId : String,context: Context){
        repo.register(userModel,userId,context)
    }


}