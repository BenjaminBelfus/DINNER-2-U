package com.example.dinner2u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail_menu.*

class detailMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)


        dish2Button.setOnClickListener{
            val intent = Intent(this, DishDetail::class.java)
            startActivity(intent)
        }
    }
}