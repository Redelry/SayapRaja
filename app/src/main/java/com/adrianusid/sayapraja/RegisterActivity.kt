package com.adrianusid.sayapraja

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.adrianusid.sayapraja.databinding.ActivityRegisterBinding
import com.adrianusid.sayapraja.model.UserModel
import com.adrianusid.sayapraja.viewmodel.RegisterViewModel
import com.adrianusid.sayapraja.viewmodel.UserPrefViewModel
import com.adrianusid.sayapraja.viewmodel.ViewModelFactory
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks

class RegisterActivity : AppCompatActivity() {

    private val registerViewModel: RegisterViewModel by viewModels()
    private lateinit var userPrefViewModel: UserPrefViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        userPrefViewModel = obtainPrefUserPrefViewModel(this)

        binding.btnRegister.setOnClickListener {
            register()

        }

        linkSetup()

    }


    private fun register() {

        val name = binding.fullname.text.toString()
        val birth = binding.birth.text.toString()
        val phone = binding.phone.text.toString()
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()
        val id = registerViewModel.getUserId()


        when {
            name.isEmpty() -> binding.fullname.error = "Tolong isi Nama Lengkap Anda !"
            birth.isEmpty() -> binding.birth.error = "Tolong isi Tanggal Kelahiran Anda !"
            phone.isEmpty() -> binding.phone.error = "Tolong is Nomor Telepon Anda"
            username.isEmpty() -> binding.username.error = "Tolong isi Username Anda !"
            password.isEmpty() -> binding.password.error = "Tolong isi Password Anda !"
            else -> {
                Log.e("REGTEST","ERROR")
                registerViewModel.register(
                    UserModel(
                        id!!, name, birth, phone, username, password, "user",
                    ), this
                )
                registerViewModel.isSuccess.observe(this){

                    if (it){
                        startActivity(Intent(this,LoginActivity::class.java))
                        finish()
                    }
                }
                userPrefViewModel.setId(id)
            }
        }



    }

    private fun linkSetup() {
        val registerLink = Link("Disini")
            .setTextColor(Color.parseColor("#004CE8"))
            .setHighlightAlpha(.4f)
            .setUnderlined(false)
            .setBold(false)
            .setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        val register = findViewById<TextView>(R.id.reg_link)
        register.applyLinks(registerLink)
    }

    private fun obtainPrefUserPrefViewModel(activity: AppCompatActivity): UserPrefViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProvider(activity, factory)[UserPrefViewModel::class.java]

    }
}