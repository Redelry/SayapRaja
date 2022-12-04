package com.adrianusid.sayapraja

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adrianusid.sayapraja.adapters.JobListAdapter
import com.adrianusid.sayapraja.databinding.ActivityHomeCompanyBinding
import com.adrianusid.sayapraja.databinding.ConfirmDialogBinding
import com.adrianusid.sayapraja.listeners.OnDeleteClickListener
import com.adrianusid.sayapraja.model.ListJobModel
import com.adrianusid.sayapraja.viewmodel.CorpPrefViewModel
import com.adrianusid.sayapraja.viewmodel.JobViewModel
import com.adrianusid.sayapraja.viewmodel.UserPrefViewModel
import com.adrianusid.sayapraja.viewmodel.ViewModelFactory

class HomeCompanyActivity : AppCompatActivity() {

    private val binding: ActivityHomeCompanyBinding by lazy {
        ActivityHomeCompanyBinding.inflate(layoutInflater)
    }

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: JobListAdapter? = null
    private val jobViewModel: JobViewModel by viewModels()
    private lateinit var corpPrefViewModel: CorpPrefViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        val dialog = Dialog(this)
        val dialogBinding = ConfirmDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        corpPrefViewModel = obtainPrefCorpPrefViewModel(this)


            corpPrefViewModel.getId().observe(this){
                jobViewModel.getData(it)
                Log.d("Data", it)
            }

        jobViewModel.data().observe(this){
            if (it != null) adapter?.setJob(it)
            Log.d("DataJob", "$it")

        }

        jobViewModel.message.observe(this){
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        }

        adapter = JobListAdapter()
        layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)


        binding.rvListJob.layoutManager = layoutManager
        binding.rvListJob.setHasFixedSize(true)
        binding.rvListJob.adapter = adapter

        adapter?.setOnDeleteClickListener(object : OnDeleteClickListener{
            override fun onDeleteClick(data: ListJobModel) {
                dialog.show()
                dialogBinding.cancel.setOnClickListener { dialog.dismiss() }
                dialogBinding.save.setOnClickListener {
                    data.idJob?.let { id -> jobViewModel.deleteJob(id) }
                    jobViewModel.isSuccess.observe(this@HomeCompanyActivity){
                        if (it){
                            Toast.makeText(this@HomeCompanyActivity,"Job Deleted", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        }
                    }
                }
            }
        })

        binding.btnAddLowongan.setOnClickListener {
            startActivity(Intent(this,AddJobActivity::class.java))
            finish()
        }

        binding.fabLogout.setOnClickListener {
            corpPrefViewModel.setId("")
            corpPrefViewModel.saveLogin(false)
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

    }
    private fun obtainPrefCorpPrefViewModel(activity: AppCompatActivity): CorpPrefViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProvider(activity, factory)[CorpPrefViewModel::class.java]

    }
}