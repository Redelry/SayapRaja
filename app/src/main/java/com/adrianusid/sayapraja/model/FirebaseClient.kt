package com.adrianusid.sayapraja.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adrianusid.sayapraja.data.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class FirebaseClient {
    private val ref = DatabaseReference.getDbRef()

    val id = ref.push().key
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess : LiveData<Boolean> = _isSuccess

    fun register(userModel: UserModel, context: Context) {
    val userRef = ref.child("user")
        if (id != null) {
           userRef.child(id).setValue(userModel).addOnCompleteListener {
               _isSuccess.value = true
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            }


        }
        Log.e("WARZONEKNTL","REG ERROR")

    }

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _message = MutableLiveData<String>()
    val msg: LiveData<String> = _message

    private val _role = MutableLiveData<String>()
    val role : LiveData<String> = _role

    fun login(loginModel: LoginModel, id: String) {

        val userRef = ref.child("user")

//            _username.postValue(ref.child("user").child(id).child("username").get().)
//        _password.postValue(ref.child("user").child(id).child("password").get().result.getValue(String::class.java))
            val query = userRef.orderByChild("id").equalTo(id)
        Log.e("ASU", id)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        _username.postValue(
                            snapshot.child(id).child("username").getValue(String::class.java)
                        )
                        _password.postValue(
                            snapshot.child(id).child("password").getValue(String::class.java)
                        )

                        _role.postValue(
                            snapshot.child(id).child("role").getValue(String::class.java)
                        )

                    } else {
                        _message.postValue("snapshot is not exists")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    _message.postValue(error.toString())
                }

            })



    }


}