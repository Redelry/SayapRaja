package com.adrianusid.sayapraja

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.adrianusid.sayapraja.databinding.ActivitySplashBinding
import com.adrianusid.sayapraja.viewmodel.CorpPrefViewModel
import com.adrianusid.sayapraja.viewmodel.LoginViewModel
import com.adrianusid.sayapraja.viewmodel.UserPrefViewModel
import com.adrianusid.sayapraja.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {


    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var userPrefViewModel: UserPrefViewModel
    private lateinit var corpPrefViewModel: CorpPrefViewModel
private val id : String? = null
    private var role : String? = null
    private var login: Boolean = true
    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        userPrefViewModel = obtainPrefUserPrefViewModel(this)

        corpPrefViewModel = obtainPrefCorpPrefViewModel(this)


        val image = binding.splashScreen
        Glide.with(this)
            .load(R.drawable.apps_logo)
            .into(image)
        Handler(Looper.getMainLooper()).postDelayed({

            loginViewModel.role.observe(this) {role =it

            }
            userPrefViewModel.getLogin().observe(this){ login = it

            }

            if (login ){
                if (role == "user"){
                    startActivity(Intent(this,HomeApplicantActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this,HomeCompanyActivity::class.java))
                    finish()
                }
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }


        }, DELAY_TIME.toLong())
    }

    private fun obtainPrefUserPrefViewModel(activity: AppCompatActivity): UserPrefViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProvider(activity, factory)[UserPrefViewModel::class.java]

    }

    private fun obtainPrefCorpPrefViewModel(activity: AppCompatActivity): CorpPrefViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProvider(activity, factory)[CorpPrefViewModel::class.java]

    }

    companion object {
        const val DELAY_TIME = 3000
    }
}