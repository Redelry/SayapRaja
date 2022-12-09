package com.adrianusid.sayapraja.data

import androidx.lifecycle.LiveData
import com.adrianusid.sayapraja.model.FirebaseClient
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CorpRepository {
    private val client = FirebaseClient()

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    val corpName : LiveData<String> = client.corpName
    val corpPhone : LiveData<String> = client.corpPhone
    val msg : LiveData<String> = client.msg
    fun getCorpName( id: String){
        executorService.execute {
            client.getCorpName(id)
        }
    }

    fun getPhoneCorp( id: String){
        executorService.execute {
            client.getCorpPhone(id)
        }
    }

}