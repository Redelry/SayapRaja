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
import java.util.concurrent.TimeUnit

class FirebaseClient {
    private val ref = DatabaseReference.getDbRef()
    val idJob = ref.push().key
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess
    private var list = ArrayList<ListJobModel>()
    fun register(userModel: UserModel, userId: String, context: Context) {


        val userRef = ref.child("user")
        userRef.child(userId).setValue(userModel).addOnCompleteListener {
            _isSuccess.value = true
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }


    }

    fun addJob(addJobModel: AddJobModel, context: Context) {
        val userRef = ref.child("JobList")

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
    private var _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName
    private var _corpPhone = MutableLiveData<String>()
    val corpPhone: LiveData<String> = _corpPhone
    private var _userPhone = MutableLiveData<String>()
    val userPhone: LiveData<String> = _userPhone
    private var _data = MutableLiveData<List<ListJobModel>>()
    val data get() = _data
    private var _dataAplicant = MutableLiveData<List<ApllicantModel>>()
    val dataAplicant get() = _dataAplicant
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
                Log.d("KAZUHA", userid)
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

    fun getCorpPhone(userId: String) {
        val corpRef = ref.child("user")
        val query = corpRef.orderByChild("userId").equalTo(userId)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    _corpPhone.postValue(
                        snapshot.child(userId).child("phone").getValue(String::class.java)

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

    fun getUserPhone(userId: String) {
        val corpRef = ref.child("user")
        val query = corpRef.orderByChild("userId").equalTo(userId)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    _userPhone.postValue(
                        snapshot.child(userId).child("phone").getValue(String::class.java)

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

    fun getUserName(userId: String) {
        val userRef = ref.child("user")
        val query = userRef.orderByChild("userId").equalTo(userId)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    _userName.postValue(
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

    fun getJobListCorp(idJob: String,idCorp: String) {
        val jobRef = ref.child("JobList").child(idJob).orderByChild("idCorp").equalTo(idCorp)
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




    fun getJobListUser(idJob: String) {
        val jobRef = ref.child("JobList").child(idJob)
        jobRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    list.clear()
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

    fun searchJob(newText: String,idJob: String) {
        val jobRef = ref.child("JobList").child(idJob).orderByChild("positionJob").startAt(newText).endAt(newText+"\uf8ff")

        jobRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {


                    for (data  in snapshot.children) {

                        val jobs = data.getValue(ListJobModel::class.java)

                            if (jobs?.positionJob == newText){
                                list.add(jobs)
                                _data.postValue(list)

                            }



                        Log.d("Mizuki", list.size.toString())
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

    fun editJob(
        positionJob: String,
        description: String,
        requirement: String,
        date: String,

        idJob: String
    ) {

        val jobRef = ref.child("JobList")
        jobRef.child(idJob).child("positionJob").setValue(positionJob)
        jobRef.child(idJob).child("description").setValue(description)
        jobRef.child(idJob).child("requirement").setValue(requirement)
        jobRef.child(idJob).child("date").setValue(date)
    }

    fun deleteJob(idJob: String) {
        val userRef = ref.child("JobList")

        userRef.child(idJob).setValue(null).addOnCompleteListener {
            _isSuccess.value = true
        }


    }
}