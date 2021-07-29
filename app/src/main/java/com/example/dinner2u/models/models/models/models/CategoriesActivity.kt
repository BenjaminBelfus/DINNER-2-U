package com.example.dinner2u.models.models.models.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dinner2u.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        push.setOnClickListener{
            val intent = Intent(this, DiscoverActivity::class.java)
            startActivity(intent)
        }

        var url = "https://www.papercitymag.com/wp-content/uploads/2018/06/MF-Sushi_interior_Photo-courtesy-of-MF-Sushi-Houston-Facebook.jpg"
        Picasso.with(this).load(url).into(image1)
    }


}