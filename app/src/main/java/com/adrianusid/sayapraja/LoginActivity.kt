package com.adrianusid.sayapraja

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        linkSetup()
    }

    private fun linkSetup() {
        val registerLink = Link("Disini")
            .setTextColor(Color.parseColor("#004CE8"))
            .setHighlightAlpha(.4f)
            .setUnderlined(false)
            .setBold(false)
            .setOnClickListener {
                startActivity(Intent(this,RegisterActivity::class.java))
            }
        val register = findViewById<TextView>(R.id.reg_link)
        register.applyLinks(registerLink)
    }
}