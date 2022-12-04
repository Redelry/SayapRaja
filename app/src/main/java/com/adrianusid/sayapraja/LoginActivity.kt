package com.adrianusid.sayapraja

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.adrianusid.sayapraja.databinding.ActivityLoginBinding
import com.adrianusid.sayapraja.viewmodel.CorpPrefViewModel
import com.adrianusid.sayapraja.viewmodel.LoginViewModel
import com.adrianusid.sayapraja.viewmodel.UserPrefViewModel
import com.adrianusid.sayapraja.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var userPrefViewModel: UserPrefViewModel
    private lateinit var corpPrefViewModel: CorpPrefViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
        userPrefViewModel = obtainPrefUserPrefViewModel(this)

        corpPrefViewModel = obtainPrefCorpPrefViewModel(this)
        val sharedPref = this.getSharedPreferences("pref", 0)
        loginViewModel.user.observe(this) { user ->
            sharedPref.edit().putBoolean("login",true).apply()
            sharedPref.edit().putString("role",user.role).apply()
            if (user.role == "user") {


                userPrefViewModel.setId(user.userId)
                startActivity(
                    Intent(
                        this,
                        HomeApplicantActivity::class.java
                    )
                )
                finish()
            } else {


                corpPrefViewModel.setId(user.userId)
                startActivity(
                    Intent(
                        this,
                        HomeCompanyActivity::class.java
                    )
                )
                finish()
            }
        }


        binding.btnLogin.setOnClickListener {
            login()
        }




        linkSetup()
    }

    private fun linkSetup() {
        val registerLink = Link("Disini")
            .setTextColor(Color.parseColor("#004CE8"))
            .setHighlightAlpha(.4f)
            .setUnderlined(false)
            .setBold(false)
            .setOnClickListener {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            }
        val register = findViewById<TextView>(R.id.reg_link)
        register.applyLinks(registerLink)
    }

    //    private fun login(id: String) {
    private fun login() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()


        //validasi Email
        if (email.isEmpty()) {
            binding.email.error = "Tolong isi Email Anda !"
        }

        //email tidak sesuai
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.email.error = "Email tidak valid !"
        }
        //validasi password
        else if (password.isEmpty()) {
            binding.password.error = "Tolong isi PAssword Anda !"
        }

        //validasi panjag password
        else if (password.length < 6) {
            binding.password.error = "Password minimal 6 karakter"
        } else {


            loginFirebase(email, password)

        }


    }

    private fun obtainPrefUserPrefViewModel(activity: AppCompatActivity): UserPrefViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProvider(activity, factory)[UserPrefViewModel::class.java]

    }

    private fun obtainPrefCorpPrefViewModel(activity: AppCompatActivity): CorpPrefViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProvider(activity, factory)[CorpPrefViewModel::class.java]

    }

    private fun loginFirebase(email: String, password: String) {


        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {

                if (it.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    val userId = user?.uid ?: ""
                    loginViewModel.login(userId)

                    Log.d("ID", userId)



                    Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()


                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }


    }


}