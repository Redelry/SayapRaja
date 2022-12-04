package com.adrianusid.sayapraja

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.adrianusid.sayapraja.databinding.ActivityAddJobBinding
import com.adrianusid.sayapraja.model.AddJobModel
import com.adrianusid.sayapraja.viewmodel.AddJobViewModel
import com.adrianusid.sayapraja.viewmodel.CorpNameViewModel
import com.adrianusid.sayapraja.viewmodel.CorpPrefViewModel
import com.adrianusid.sayapraja.viewmodel.ViewModelFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddJobActivity : AppCompatActivity() {

    private val binding: ActivityAddJobBinding by lazy {
        ActivityAddJobBinding.inflate(layoutInflater)
    }

    private val addJobViewModel: AddJobViewModel by viewModels()
    private val corpNameViewModel: CorpNameViewModel by viewModels()
    private lateinit var corpPrefViewModel: CorpPrefViewModel
    private var idCorp: String? = null
    private var corpName: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        corpPrefViewModel = obtainPrefCorpPrefViewModel(this)

        corpPrefViewModel.getId().observe(this) {
            idCorp = it
            Log.d("IDCORP",it)
            corpNameViewModel.getCorpName(it)

        }

        corpNameViewModel.corpName.observe(this) {
            corpName = it


            Log.d("ASUS", corpName.toString())
            Log.e("BODO", "ERROR")

        }
        binding.btnUpload.setOnClickListener {
            if (idCorp != null && corpName != null) {
                uploadJob(idCorp!!, corpName!!)
            }

        }



    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun uploadJob(idCorp: String, corpName: String) {

        val position = binding.positionInput.text.toString()

        val description = binding.descriptionInput.text.toString()
        val requirement = binding.requirementInput.text.toString()
        val idJob = addJobViewModel.getJobId()
        val date = humanDateFormat(LocalDateTime.now())



        when {
            position.isEmpty() -> binding.positionInput.error =
                "Tolong isi posisi pekerjaan yang dibutuhkan oleh perusahaan anda !"
            description.isEmpty() -> binding.descriptionInput.error =
                "Tolong isi deskripsi pekerjaan yang ingin ditawarkan !"
            requirement.isEmpty() -> binding.requirementInput.error =
                "Tolong isi Syarat yang diperlukan agar pelamar bisa melamar posisi ini !"


            else -> {


                addJobViewModel.addJob(
                    AddJobModel(
                        idCorp, idJob.toString(), corpName, position, description, requirement, date
                    ), this, idCorp
                )
                addJobViewModel.isSuccess.observe(this) {
                    if (it) {
                        startActivity(Intent(this, HomeCompanyActivity::class.java))
                        finish()

                    }
                }
                corpNameViewModel.msg.observe(this) {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }

            }

        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun humanDateFormat(date: LocalDateTime) =
        date.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy"))


    private fun obtainPrefCorpPrefViewModel(activity: AppCompatActivity): CorpPrefViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProvider(activity, factory)[CorpPrefViewModel::class.java]

    }


}