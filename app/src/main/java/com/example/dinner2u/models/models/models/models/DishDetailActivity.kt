package com.example.dinner2u.models.models.models.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.dinner2u.R
import com.example.dinner2u.models.models.models.dataclass.DishesDataClass
import com.example.dinner2u.models.models.models.dataclass.UserCartDataClass
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_discover.*
import kotlinx.android.synthetic.main.activity_dish_detail.*

class DishDetailActivity : AppCompatActivity() {

    //RestuarantID y DishNumber es algo que quiero pasar del previous activity a este
    val dishname:String = "California Roll"
    val restaurantID:Int = 0
    val dishesdataclass: DishesDataClass = DishesDataClass()
    val usercartdata : UserCartDataClass = UserCartDataClass()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_detail)

        update()

        addButton.setOnClickListener{
            usercartdata.itemList.add(dishname)
            var message:String = "Added to cart"
            showMessage()
        }

        //Arreglar esto pq quiero volver a la activity anterior con la data pasada..................
        bbutton.setOnClickListener{
            val intent = Intent(this, CategoriesActivity::class.java)
            startActivity(intent)
        }
    }

    fun update() {
        var values:ArrayList<String> = ArrayList()
        values = dishesdataclass.userHashmap.get(restaurantID)?.get(dishname)!!
        var dishPicture = values.get(1)
        var dishDescr = values.get(2)

        dishName.text = dishname
        Picasso.with(this).load(dishPicture).into(dishImage)
        dishDescription.text = dishDescr
    }

    fun showMessage() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.error)
        builder.setMessage("Added to cart")
        builder.setPositiveButton(R.string.okey, null)

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}