package com.adrianusid.sayapraja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.adrianusid.sayapraja.databinding.ActivityLoginBinding
import com.adrianusid.sayapraja.databinding.ActivitySplashBinding
import com.bumptech.glide.Glide

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val image = binding.splashScreen
        Glide.with(this)
            .load(R.drawable.logo)
            .into(image)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 5000)
    }
}