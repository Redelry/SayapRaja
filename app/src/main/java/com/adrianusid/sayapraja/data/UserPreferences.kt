package com.adrianusid.sayapraja.data

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class UserPreferences (private val context: Context){

    private var sharedPreferences : SharedPreferences ?= null

    private var editor : SharedPreferences.Editor ? = null

    private var id = MutableLiveData<String>()


    init {
        sharedPreferences = context.getSharedPreferences("pref", 0)
        editor = sharedPreferences?.edit()
        editor?.apply()
    }

    fun setId(id:String){
        editor?.putString("id",id)
        editor?.commit()
    }

    fun getId() : LiveData<String> {
        id.value = sharedPreferences?.getString("id","")
        return id

    }
}