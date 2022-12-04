package com.adrianusid.sayapraja.data

import androidx.lifecycle.LiveData
import com.adrianusid.sayapraja.model.FirebaseClient
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CorpNameRepository {
    private val client = FirebaseClient()

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    val corpName : LiveData<String> = client.corpName
    val msg : LiveData<String> = client.msg
    fun getCorpName( id: String){
        executorService.execute {
            client.getCorpName(id)
        }
    }
}