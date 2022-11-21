package com.adrianusid.sayapraja

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.adrianusid.sayapraja.databinding.ActivityRegisterBinding
import com.adrianusid.sayapraja.model.UserModel
import com.adrianusid.sayapraja.viewmodel.RegisterViewModel
import com.adrianusid.sayapraja.viewmodel.UserPrefViewModel
import com.adrianusid.sayapraja.viewmodel.ViewModelFactory

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

        binding.btnLogin.setOnClickListener {
            register()
        }

    }


    private fun register() {
        val name = "Budi"
        val phone = "082320010090"
        val username = "lordBudi"
        val password = "Budi123"
        val id = registerViewModel.getUserId()
//        when{
//            name.isEmpty() -> {
//
//            }
//
//        }

        registerViewModel.register(
            UserModel(
                id!!, name, phone, username, password, "user",
            ), this
        )

        userPrefViewModel.setId(id)

    }



    private fun obtainPrefUserPrefViewModel(activity: AppCompatActivity): UserPrefViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProvider(activity, factory)[UserPrefViewModel::class.java]

    }
}