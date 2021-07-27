package com.example.dinner2u.models.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.dinner2u.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setup()
    }

    fun setup() {
        loginButton.setOnClickListener{
            if (emailAddressText.text.isNotEmpty() && passwordText.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailAddressText.text.toString(), passwordText.text.toString()).addOnCompleteListener{
                    if (it.isSuccessful) {
                        val intent = Intent(this, CategoriesActivity::class.java)
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
        builder.setTitle(R.string.error)
        builder.setMessage(R.string.incorrect_email_or_password)
        builder.setPositiveButton(R.string.okey, null)

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}