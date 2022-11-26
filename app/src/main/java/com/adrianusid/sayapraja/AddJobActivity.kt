package com.adrianusid.sayapraja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adrianusid.sayapraja.databinding.ActivityAddJobBinding

class AddJobActivity : AppCompatActivity() {

    private val binding: ActivityAddJobBinding by lazy {
        ActivityAddJobBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()


    }
}