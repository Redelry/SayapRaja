package com.adrianusid.sayapraja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.adrianusid.sayapraja.databinding.ActivityHomeApplicantBinding
import com.adrianusid.sayapraja.viewmodel.UserPrefViewModel
import com.adrianusid.sayapraja.viewmodel.ViewModelFactory

class HomeApplicantActivity : AppCompatActivity() {

    private val binding: ActivityHomeApplicantBinding by lazy {
        ActivityHomeApplicantBinding.inflate(layoutInflater)
    }

    private lateinit var userPrefViewModel: UserPrefViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        userPrefViewModel = obtainPrefUserPrefViewModel(this)

        binding.fabLogout.setOnClickListener {
            userPrefViewModel.setId("")
            userPrefViewModel.saveLogin(false)
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

    }

    private fun obtainPrefUserPrefViewModel(activity: AppCompatActivity): UserPrefViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProvider(activity, factory)[UserPrefViewModel::class.java]

    }
}