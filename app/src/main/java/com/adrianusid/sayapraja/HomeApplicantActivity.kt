package com.adrianusid.sayapraja

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adrianusid.sayapraja.adapters.JobListAdapterUser
import com.adrianusid.sayapraja.databinding.ActivityHomeApplicantBinding
import com.adrianusid.sayapraja.listeners.OnCardClickListener
import com.adrianusid.sayapraja.model.ListJobModel
import com.adrianusid.sayapraja.viewmodel.JobViewModel
import com.adrianusid.sayapraja.viewmodel.UserPrefViewModel
import com.adrianusid.sayapraja.viewmodel.ViewModelFactory
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks


class HomeApplicantActivity : AppCompatActivity() {

    private val binding: ActivityHomeApplicantBinding by lazy {
        ActivityHomeApplicantBinding.inflate(layoutInflater)
    }
    private var layoutManager: RecyclerView.LayoutManager? = null
    private lateinit var adapter: JobListAdapterUser
    private val jobViewModel: JobViewModel by viewModels()
    private lateinit var userPrefViewModel: UserPrefViewModel
    private var data = ListJobModel()

    private lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val sharedPref = this.getSharedPreferences("pref", 0)
        supportActionBar?.hide()

        userPrefViewModel = obtainPrefUserPrefViewModel(this)




        adapter = JobListAdapterUser()


        jobViewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }


        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        binding.rvListJobUser.layoutManager = layoutManager
        binding.rvListJobUser.setHasFixedSize(true)
        binding.rvListJobUser.adapter = adapter
        searchView = binding.searchView


        searchView.queryHint = resources.getString(R.string.cari_lowongan)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false

            }

            override fun onQueryTextChange(newText: String): Boolean {



                data.idJob?.let { idJob -> jobViewModel.searchListJob(newText, idJob) }

                jobViewModel.data().observe(this@HomeApplicantActivity) {

                    if (it != null) adapter.setJob(it)
                    Log.d("DataJob", "$it")
                }
                return true

            }

        })


        searchView.setOnCloseListener {
            finish()
            startActivity(intent)
            true
        }
        adapter.setOnCardClickListener(object : OnCardClickListener {


            override fun onCardClickListJob(data: ListJobModel) {
                val intent = Intent(this@HomeApplicantActivity,DetailActivity::class.java)
                intent.putExtra(DetailActivity.DATA,data)
                startActivity(intent)
            }

        })

        binding.fabLogout.setOnClickListener {
            userPrefViewModel.setId("")
            sharedPref.edit().putBoolean("login", false).apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        linkSetup()

    }

    private fun linkSetup() {
        val allListLink = Link("Lihat Semua")
            .setTextColor(Color.parseColor("#004CE8"))
            .setHighlightAlpha(.4f)
            .setUnderlined(false)
            .setBold(false)
            .setOnClickListener {
                startActivity(Intent(this, ListJobActivity::class.java))

            }
        val allList = binding.tvAllList
        allList.applyLinks(allListLink)
    }


    private fun obtainPrefUserPrefViewModel(activity: AppCompatActivity): UserPrefViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProvider(activity, factory)[UserPrefViewModel::class.java]

    }
}