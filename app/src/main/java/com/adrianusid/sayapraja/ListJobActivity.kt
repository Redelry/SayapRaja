package com.adrianusid.sayapraja

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adrianusid.sayapraja.adapters.JobListAdapterUser
import com.adrianusid.sayapraja.databinding.ActivityListJobBinding
import com.adrianusid.sayapraja.listeners.OnCardClickListener
import com.adrianusid.sayapraja.model.ListJobModel
import com.adrianusid.sayapraja.viewmodel.JobViewModel
import com.adrianusid.sayapraja.viewmodel.UserPrefViewModel
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks

class ListJobActivity : AppCompatActivity() {

    private val binding: ActivityListJobBinding by lazy {
        ActivityListJobBinding.inflate(layoutInflater)
    }
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var adapter: JobListAdapterUser
    private val jobViewModel: JobViewModel by viewModels()
    private var data = ListJobModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        adapter = JobListAdapterUser()


        jobViewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }


        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        binding.rvListJob.layoutManager = layoutManager
        binding.rvListJob.setHasFixedSize(true)
        binding.rvListJob.adapter = adapter
        jobViewModel.getDataJobUser(data.idJob.toString())

        jobViewModel.data().observe(this@ListJobActivity) {

            if (it != null) adapter.setJob(it)
            Log.d("DataJob", "$it")
        }

        adapter.setOnCardClickListener(object : OnCardClickListener {


            override fun onCardClickListJob(data: ListJobModel) {
                val intent = Intent(this@ListJobActivity,DetailActivity::class.java)
                intent.putExtra(DetailActivity.DATA,data)
                startActivity(intent)
            }

        })
    }


}