package com.example.dinner2u.models.models.models.models

import android.R.attr.data
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.dinner2u.R
import com.example.dinner2u.models.models.models.adapters.UserModel
import com.example.dinner2u.models.models.models.adapters.UsersDBHelper
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*


class RegisterActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    lateinit var usersDBHelper: UsersDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        usersDBHelper = UsersDBHelper(this)
        setup2()
    }

    fun setup2() {
        registerButton.setOnClickListener{
            if (registerNameText.text.isNotEmpty() && registerLastNameText.text.isNotEmpty()
                && registerEmailText.text.isNotEmpty() && registerPasswordText.text.isNotEmpty()) {
                val user = UserModel(UUID.randomUUID().toString(), registerNameText.text.toString(), registerLastNameText.text.toString(),
                    registerEmailText.text.toString(), registerPasswordText.text.toString(), "7/29/2021", registerMainAddress.text.toString())
                var result = usersDBHelper.insertUser(user)
                Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CategoriesActivity::class.java)
                startActivity(intent)
            }
        }
    }


    fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.error)
        builder.setMessage(R.string.an_error_has_occurred)
        builder.setPositiveButton(R.string.okey, null)

        val dialog:AlertDialog = builder.create()
        dialog.show()
    }


   //this was used for firebase
//    fun setup() {
//        registerButton.setOnClickListener{
//            if (registerEmailText.text.isNotEmpty() && registerPasswordText.text.isNotEmpty()) {
//                FirebaseAuth.getInstance().createUserWithEmailAndPassword(registerEmailText.text.toString(), registerPasswordText.text.toString()).addOnCompleteListener{
//                    if (it.isSuccessful) {
//                        val name = registerNameText.text.toString()
//                        val email = it.result?.user?.email?: ""
//                        val uid = it.result?.user?.uid?: ""
//                        saveUserData(name, email, uid)
//                    } else {
//                        showAlert()
//                    }
//                }
//            }
//        }
//    }


//    fun saveUserData(name: String, email: String, uid: String) {
//        val user = hashMapOf(
//            "name" to name,
//            "email" to email,
//            "uid" to uid
//        )
//        db.collection("users").document(uid).set(user).addOnSuccessListener {
//            val intent = Intent(this, DiscoverActivity::class.java)
//            startActivity(intent)
//        }.addOnFailureListener{
//            showAlert()
//        }
//    }
}