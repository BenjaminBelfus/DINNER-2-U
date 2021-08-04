package com.example.dinner2u.models.models.models.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.dinner2u.R
import com.example.dinner2u.models.models.models.database.cart.CartDBHelper
import com.example.dinner2u.models.models.models.database.cart.CartModel
import com.example.dinner2u.models.models.models.database.dishes.DishesModel
import com.example.dinner2u.models.models.models.database.restaurants.RestaurantModel
import com.example.dinner2u.models.models.models.models.managers.DataManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_dish_detail.*
import java.util.*

class DishDetailActivity : AppCompatActivity() {

    private lateinit var dish: DishesModel
    private lateinit var restaurant: RestaurantModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_detail)


        // bundle is used to read extras from previous activity
        val bundle: Bundle? = intent.extras
        dish = bundle?.getSerializable("dish") as DishesModel
        restaurant = bundle?.getSerializable("restaurant") as RestaurantModel

        updateDish()

        dishDetailBackButton.setOnClickListener{
            //this.finish()
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }


        dishDetailAddButton.setOnClickListener{
            val cartDBHelper = CartDBHelper(this)
            val cart = CartModel(UUID.randomUUID().toString(), restaurant.id, dish.id, DataManager.getCurrentUserID())
            cartDBHelper.insertCart(cart)
            Toast.makeText(this, "Added to cart", Toast.LENGTH_LONG).show()
        }
    }

    fun updateDish() {
        dishName.text = dish.name
        Picasso.with(this).load(dish.picture).into(dishImage)
        dishDetailDescription.text = dish.description
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