package com.adrianusid.sayapraja

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.adrianusid.sayapraja.databinding.ActivityEditJobBinding
import com.adrianusid.sayapraja.model.ListJobModel
import com.adrianusid.sayapraja.viewmodel.CorpPrefViewModel
import com.adrianusid.sayapraja.viewmodel.JobViewModel
import com.adrianusid.sayapraja.viewmodel.ViewModelFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditJobActivity : AppCompatActivity() {

    private val binding: ActivityEditJobBinding by lazy {
        ActivityEditJobBinding.inflate(layoutInflater)
    }
    private val jobViewModel: JobViewModel by viewModels()
    private lateinit var corpPrefViewModel: CorpPrefViewModel
    private var idCorp: String? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<ListJobModel>(DATA) as ListJobModel
        corpPrefViewModel = obtainPrefCorpPrefViewModel(this)


        corpPrefViewModel.getId().observe(this) {
            idCorp = it

        }

        binding.positionInput.setText(data.positionJob)
        binding.descriptionInput.setText(data.description)
        binding.requirementInput.setText(data.requirement)

        binding.cancel.setOnClickListener { finish() }

        binding.btnSave.setOnClickListener {
            val position = binding.positionInput.text.toString()
            val description = binding.descriptionInput.text.toString()
            val requirement = binding.requirementInput.text.toString()
            val dateUpdate = "Edited on ${humanDateFormat(LocalDateTime.now())}"

            when {
                position.isEmpty() -> binding.positionInput.error =
                    "Tolong isi posisi pekerjaan yang dibutuhkan oleh perusahaan anda !"
                description.isEmpty() -> binding.descriptionInput.error =
                    "Tolong isi deskripsi pekerjaan yang ingin ditawarkan !"
                requirement.isEmpty() -> binding.requirementInput.error =
                    "Tolong isi Syarat yang diperlukan agar pelamar bisa melamar posisi ini !"
                else -> {
                    data.idJob?.let { id ->
                        jobViewModel.editJob(
                            position,
                            description,
                            requirement,
                            dateUpdate,
                            idCorp!!,
                            id
                        )
                    }

                    Toast.makeText(this, "Note Edited!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeCompanyActivity::class.java))
                    finish()
                }

            }

        }

    }

    private fun obtainPrefCorpPrefViewModel(activity: AppCompatActivity): CorpPrefViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProvider(activity, factory)[CorpPrefViewModel::class.java]

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun humanDateFormat(date: LocalDateTime) =
        date.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy"))

    companion object {
        const val DATA = "data"
    }
}