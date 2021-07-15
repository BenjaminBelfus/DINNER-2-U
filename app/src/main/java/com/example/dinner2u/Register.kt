package com.example.dinner2u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import org.w3c.dom.Text

class Register : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

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
                        val name = registerNameText.text.toString()
                        val email = it.result?.user?.email?: ""
                        val uid = it.result?.user?.uid?: ""
                        saveUserData(name, email, uid)
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

    fun saveUserData(name: String, email: String, uid: String) {
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "uid" to uid
        )
        db.collection("users").document(uid).set(user).addOnSuccessListener {
            val intent = Intent(this, Discover::class.java)
            startActivity(intent)
        }.addOnFailureListener{
            showAlert()
        }
    }
}