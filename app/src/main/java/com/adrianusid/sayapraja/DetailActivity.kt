package com.adrianusid.sayapraja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adrianusid.sayapraja.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()


    }
}