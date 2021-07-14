package com.example.dinner2u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.lButton)
        val registerButton = findViewById<Button>(R.id.rButton)

        loginButton.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}