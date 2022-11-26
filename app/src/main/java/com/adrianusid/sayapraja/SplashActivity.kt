package com.adrianusid.sayapraja

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.adrianusid.sayapraja.databinding.ActivitySplashBinding
import com.bumptech.glide.Glide

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        val image = binding.splashScreen
        Glide.with(this)
            .load(R.drawable.apps_logo)
            .into(image)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, DELAY_TIME.toLong())
    }

    companion object {
        const val DELAY_TIME = 3000
    }
}