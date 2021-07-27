package com.example.dinner2u.models.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dinner2u.R
import kotlinx.android.synthetic.main.activity_detail_menu.*

class DetailMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)


        dish2Button.setOnClickListener{
            val intent = Intent(this, DishDetailActivity::class.java)
            startActivity(intent)
        }
    }
}