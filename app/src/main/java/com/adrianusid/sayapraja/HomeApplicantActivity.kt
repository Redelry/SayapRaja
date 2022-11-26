package com.adrianusid.sayapraja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adrianusid.sayapraja.databinding.ActivityHomeApplicantBinding

class HomeApplicantActivity : AppCompatActivity() {

    private val binding: ActivityHomeApplicantBinding by lazy {
        ActivityHomeApplicantBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()


    }
}