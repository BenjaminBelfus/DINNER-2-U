package com.example.dinner2u.models.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dinner2u.R
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        discoverButton.setOnClickListener{
            val intent = Intent(this, DiscoverActivity::class.java)
            startActivity(intent)
        }
    }
}