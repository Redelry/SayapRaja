package com.adrianusid.sayapraja

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.adrianusid.sayapraja.data.DatabaseReference
import com.adrianusid.sayapraja.databinding.ActivityDetailBinding
import com.adrianusid.sayapraja.model.ApllicantModel
import com.adrianusid.sayapraja.model.ListJobModel
import com.adrianusid.sayapraja.model.UploadModel
import com.adrianusid.sayapraja.viewmodel.UserPrefViewModel
import com.adrianusid.sayapraja.viewmodel.UserViewModel
import com.adrianusid.sayapraja.viewmodel.ViewModelFactory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    private val ref = DatabaseReference.getDbRef()
    private val storageRef: StorageReference = FirebaseStorage.getInstance().reference
    private val idPdf = ref.push().key
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess
    private var userId: String? = null
    private var userName: String? = null
    private var userPhone: String? = null
    private val _message = MutableLiveData<String>()
    val msg: LiveData<String> = _message
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var userPrefViewModel: UserPrefViewModel
    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        userPrefViewModel = obtainPrefUserPrefViewModel(this)
        userPrefViewModel.getId().observe(this) {
            userId = it
            Log.d("IDUSER", it)
            val data = ApllicantModel()
            val datas = intent.getParcelableExtra<ListJobModel>(DATA) as ListJobModel
            getApplicantListUser(
                data.idPdf.toString(), it,
                datas.idJob.toString()
            )
            Log.d("IDJOB", "${datas.idJob}")
            userViewModel.getUserName(it)
            userViewModel.getUserPhone(it)
        }
        userViewModel.userPhone.observe(this){
            userPhone = it
        }
        userViewModel.userName.observe(this) {
            userName = it
            Log.d("ASUS", userName.toString())
            Log.e("BODO", "ERROR")
        }


        val data = intent.getParcelableExtra<ListJobModel>(DATA) as ListJobModel
        binding.corpName.text = data.corpName
        binding.phoneNumber.text = data.phone
        binding.tvJobPosition.text = data.positionJob
        binding.tvJobDesc.text = data.description
        binding.tvJobReq.text = data.requirement



        binding.btnUpload.setOnClickListener {
            selectFiles()
        }

    }

    private fun selectFiles() {
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Pilih File PDF Lamaran CV"), 1)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.data != null) {
            uploadFiles(data.data!!, userName.toString(), userId.toString(), idPdf.toString(),userPhone.toString())
        }
    }

    private fun uploadFiles(
        data: Uri,
        userName: String,
        userId: String,
        idPdf: String,
        phone: String
    ) {
        dialog = ProgressDialog(this)
        dialog!!.setTitle("Sedang Upload...")
        dialog!!.show()

        val refStorage: StorageReference =
            storageRef.child("Uploads/" + System.currentTimeMillis() + ".pdf")
        refStorage.putFile(data).addOnSuccessListener {
            refStorage.downloadUrl.addOnSuccessListener { uri ->
                val intentData = intent.getParcelableExtra<ListJobModel>(DATA) as ListJobModel
                val pdfClass = UploadModel(
                    idPdf,
                    userId,
                    userName,
                    uri.toString(),
                    intentData.idCorp.toString(),
                    intentData.idJob.toString(),
                    phone
                )


                val upRef = ref.child("UploadPdf")
                upRef.child(idPdf).setValue(pdfClass)
            }
            Toast.makeText(this@DetailActivity, "File Uploaded", Toast.LENGTH_SHORT).show()
            dialog!!.dismiss()

        }.addOnProgressListener { snapshot ->
            val progress: Double = (100.0 * snapshot.bytesTransferred) / snapshot.totalByteCount
            dialog!!.setMessage("Uploaded: " + progress.toInt() + "%")
        }


    }

    private fun getApplicantListUser(idPdf: String, userId: String, idJob: String) {
        val jobRef = ref.child("UploadPdf").child(idPdf).orderByChild("userId").equalTo(userId)

        jobRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (data in snapshot.children) {

                        val jobs = data.getValue(ApllicantModel::class.java)
                        val jobList = ArrayList<ApllicantModel>()
                        if (jobs?.idJob == idJob) {
                            jobList.add(jobs)
                            Log.d("DATA1", "$jobs")
                            Log.d("Data2", "$jobList")
                        }

                        Log.d("YUKIMURA", jobList.size.toString())
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


    companion object {
        const val DATA = "data"
    }

    private fun obtainPrefUserPrefViewModel(activity: AppCompatActivity): UserPrefViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProvider(activity, factory)[UserPrefViewModel::class.java]

    }
}