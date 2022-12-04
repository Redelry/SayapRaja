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
    val idJob = ref.push().key
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun register(userModel: UserModel, userId: String,context: Context) {


        val userRef = ref.child("user")
        userRef.child(userId).setValue(userModel).addOnCompleteListener {
            _isSuccess.value = true
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }


    }

    fun addJob(addJobModel: AddJobModel, context: Context, idCorp: String) {
        val userRef = ref.child("JobList").child(idCorp)

        userRef.child(idJob.toString()).setValue(addJobModel).addOnCompleteListener {
            _isSuccess.value = true
            Toast.makeText(context, "Job Uploaded Successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener { err ->
            _isSuccess.value = false
            Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_SHORT).show()
        }


    }

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _user = MutableLiveData<UserModel>()
    val users: LiveData<UserModel> = _user

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _message = MutableLiveData<String>()
    val msg: LiveData<String> = _message

    private var _role = MutableLiveData<String>()
    val role: LiveData<String> = _role

    private var _corpName = MutableLiveData<String>()
    val corpName: LiveData<String> = _corpName

    private var _data = MutableLiveData<List<ListJobModel>>()
    val data get() = _data

    fun login(userid: String) {

        val userRef = ref.child("user")


        val query = userRef.orderByChild("userId").equalTo(userid)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    val user = UserModel(
                        userId = userid,
                        email = snapshot.child(userid).child("email").getValue(String::class.java)
                            .toString(),
                        role = snapshot.child(userid).child("role").getValue(String::class.java)
                            .toString(),
                        password = snapshot.child(userid).child("password")
                            .getValue(String::class.java).toString()
                    )

                    _user.postValue(
                        user
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

    fun getCorpName(userId: String) {
        val corpRef = ref.child("user")
        val query = corpRef.orderByChild("userId").equalTo(userId)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    _corpName.postValue(
                        snapshot.child(userId).child("name").getValue(String::class.java)

                    )

                } else {
                    _message.postValue("snapshot is not exists")
                    Log.d("ASU", _message.toString())
                }
                Log.d("KAZUHA", userId)
            }

            override fun onCancelled(error: DatabaseError) {
                _message.postValue(error.toString())
            }
        })
        Log.d("KAZUHA", userId)

    }

    fun getJobListCorp(idCorp: String) {
        val jobRef = ref.child("JobList").child(idCorp)

        jobRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {


                    for (data in snapshot.children) {

                        val jobs = data.getValue(ListJobModel::class.java)
                        val jobList = ArrayList<ListJobModel>()
                        jobList.add(jobs!!)
                        _data.value = jobList

                        Log.d("Mizuki", jobList.size.toString())
                    }
                } else {
                    Log.e("Mizuki", "NoDATA")

                }


            }

            override fun onCancelled(error: DatabaseError) {
                _message.value = error.message
            }
        })
    }

    fun editJob(position: String, description: String, requirement: String, date: String, idCorp: String, idJob: String) {

        val jobRef = ref.child("JobList").child(idCorp)
        jobRef.child(idJob).child("position").setValue(position)
        jobRef.child(idJob).child("description").setValue(description)
        jobRef.child(idJob).child("requirement").setValue(requirement)
        jobRef.child(idJob).child("date").setValue(date)
    }

    fun deleteJob(idJob: String,idCorp: String) {
        val userRef = ref.child("JobList").child(idCorp)

        userRef.child(idJob).setValue(null).addOnCompleteListener {
            _isSuccess.value = true
        }



    }
}