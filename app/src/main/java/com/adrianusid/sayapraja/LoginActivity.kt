package com.adrianusid.sayapraja

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.adrianusid.sayapraja.databinding.ActivityLoginBinding
import com.adrianusid.sayapraja.model.LoginModel
import com.adrianusid.sayapraja.viewmodel.LoginViewModel
import com.adrianusid.sayapraja.viewmodel.UserPrefViewModel
import com.adrianusid.sayapraja.viewmodel.ViewModelFactory
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks

class LoginActivity : AppCompatActivity() {

    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var userPrefViewModel: UserPrefViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        userPrefViewModel = obtainPrefUserPrefViewModel(this)



        userPrefViewModel.getId().observe(this){ id ->
            binding.btnLogin.setOnClickListener {
                login(id)
            }
        }
        linkSetup()

        testIntent()
    }

    private fun testIntent() {
        val intentListJob = Link("")
            .setOnClickListener {
                startActivity(Intent(this, ListJobActivity::class.java))
            }
        val intent = binding.testIntent
        intent.applyLinks(intentListJob)
    }

    private fun linkSetup() {
        val registerLink = Link("Disini")
            .setTextColor(Color.parseColor("#004CE8"))
            .setHighlightAlpha(.4f)
            .setUnderlined(false)
            .setBold(false)
            .setOnClickListener {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        val register = findViewById<TextView>(R.id.reg_link)
        register.applyLinks(registerLink)
    }

    private fun login(id: String) {
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()

        when {
            username.isEmpty() -> binding.username.error = "Please Insert Your Username"

            password.isEmpty() -> binding.password.error = "Please Insert Your Password"

            else -> {
                loginViewModel.login(LoginModel(username, password), id)


                loginViewModel.username.observe(this) {

                    loginViewModel.password.observe(this) { pass ->
                        when {
                            username != it -> Toast.makeText(
                                this,
                                "Check Your Username Again",
                                Toast.LENGTH_SHORT
                            ).show()

                            password !=  pass-> Toast.makeText(
                                this,
                                "Check Your Password Again",
                                Toast.LENGTH_SHORT
                            ).show()

                            else -> {
                                loginViewModel.role.observe(this){
                                    if(it == "user"){
                                        startActivity(Intent(this, HomeApplicantActivity::class.java))
                                        finish()
                                    } else {
                                        startActivity(Intent(this, HomeCompanyActivity::class.java))
                                        finish()
                                    }
                                }

                            }
                        }

                    }

                }


                loginViewModel.msg.observe(this) {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    private fun obtainPrefUserPrefViewModel(activity: AppCompatActivity): UserPrefViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProvider(activity, factory)[UserPrefViewModel::class.java]

    }

}