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
import com.adrianusid.sayapraja.data.DatabaseReference
import com.adrianusid.sayapraja.databinding.ActivityRegisterBinding
import com.adrianusid.sayapraja.model.UserModel
import com.adrianusid.sayapraja.viewmodel.RegisterViewModel
import com.adrianusid.sayapraja.viewmodel.UserPrefViewModel
import com.adrianusid.sayapraja.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var root: FirebaseDatabase
    private val ref = DatabaseReference.getDbRef()
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }
    private val registerViewModel: RegisterViewModel by viewModels()
    private lateinit var userPrefViewModel: UserPrefViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        supportActionBar?.hide()

        userPrefViewModel = obtainPrefUserPrefViewModel(this)

        binding.btnRegister.setOnClickListener {
            register()

        }

        linkSetup()

    }


    private fun register() {

        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val name = binding.fullname.text.toString()
        val phone = binding.phone.text.toString()
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
        }

        //validasi nama
        else if (name.isEmpty()) {
            binding.fullname.error = "Tolong isi Nama Lengkap Anda !"
        }
        //validasi no telepon
        else if (phone.isEmpty()) {
            binding.phone.error = "Tolong is Nomor Telepon Anda"
        }
//        if (!Patterns.PHONE.matcher(phone).matches()) {
//            binding.email.error = "Email tidak valid !"
//        }
        //validasi jumlah karakter nomor telepon
        else if (phone.length < 12) {
            binding.phone.error = "Nomor Telepon minimal 12 karakter"
        }else {
            registerFirebase(name, email, password, phone)
        }




    }

    private fun linkSetup() {
        val loginLink = Link("Disini")
            .setTextColor(Color.parseColor("#004CE8"))
            .setHighlightAlpha(.4f)
            .setUnderlined(false)
            .setBold(false)
            .setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        val register = findViewById<TextView>(R.id.login_link)
        register.applyLinks(loginLink)
    }

    private fun obtainPrefUserPrefViewModel(activity: AppCompatActivity): UserPrefViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)

        return ViewModelProvider(activity, factory)[UserPrefViewModel::class.java]

    }

    private fun registerFirebase(name: String, email: String, password: String, phone: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    upData(name, email, password, phone)
                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun upData(name: String, email: String, password: String, phone: String) {


        val user: FirebaseUser? = auth.currentUser
        val userId = user!!.uid


        registerViewModel.register(
            UserModel(
                userId, name, phone, email, password, "user",
            ), this
        )
    }
}




