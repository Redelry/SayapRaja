package com.adrianusid.sayapraja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adrianusid.sayapraja.databinding.ActivityHomeCompanyBinding

class HomeCompanyActivity : AppCompatActivity() {

    private val binding: ActivityHomeCompanyBinding by lazy {
        ActivityHomeCompanyBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()


    }
}