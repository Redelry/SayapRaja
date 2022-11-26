package com.adrianusid.sayapraja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adrianusid.sayapraja.databinding.ActivityListJobBinding

class ListJobActivity : AppCompatActivity() {

    private val binding: ActivityListJobBinding by lazy {
        ActivityListJobBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()


    }
}