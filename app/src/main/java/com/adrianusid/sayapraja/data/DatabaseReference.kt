package com.adrianusid.sayapraja.data

import com.google.firebase.database.FirebaseDatabase

object DatabaseReference {

    private val ref = FirebaseDatabase.getInstance().reference.child("data")
    fun getDbRef() = ref


}