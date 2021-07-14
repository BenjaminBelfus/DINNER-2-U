package com.example.dinner2u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setup()
    }

    fun setup() {
        registerButton.setOnClickListener{
            if (registerEmailText.text.isNotEmpty() && registerPasswordText.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(registerEmailText.text.toString(), registerPasswordText.text.toString()).addOnCompleteListener{
                    if (it.isSuccessful) {
                        val intent = Intent(this, Discover::class.java)
                        startActivity(intent)
                    } else {
                        showAlert()
                    }
                }
            }
        }
    }

    fun showAlert() {
       val builder = AlertDialog.Builder(this)
       builder.setTitle("Error")
       builder.setMessage("An error has occured when creating user")
       builder.setPositiveButton("Okey", null)

       val dialog:AlertDialog = builder.create()
       dialog.show()
    }
}