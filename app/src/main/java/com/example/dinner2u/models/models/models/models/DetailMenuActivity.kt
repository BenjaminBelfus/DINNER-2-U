package com.example.dinner2u.models.models.models.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dinner2u.R
import com.example.dinner2u.models.models.models.database.categories.CategoryModel
import com.example.dinner2u.models.models.models.database.dishes.DishesDBHelper
import com.example.dinner2u.models.models.models.database.dishes.DishesModel
import com.example.dinner2u.models.models.models.database.menu.MenuDBHelper
import com.example.dinner2u.models.models.models.database.menu.MenuModel
import com.example.dinner2u.models.models.models.database.restaurants.RestaurantModel
import com.example.dinner2u.models.models.models.database.restaurants.RestaurantsDBHelper
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.activity_detail_menu.*
import java.util.*

class DetailMenuActivity : AppCompatActivity() {

    //Variables used for local database
    private val dishesList = arrayListOf<DishesModel>()
    private lateinit var restaurant: RestaurantModel

    private lateinit var myAdapter: DetailMenuAdapter
    private lateinit var menuDBHelper: MenuDBHelper



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)

        menuRecycler_view.layoutManager = LinearLayoutManager(this)
        menuRecycler_view.setHasFixedSize(true)

        myAdapter = DetailMenuAdapter(dishesList)

        menuDBHelper = MenuDBHelper(this)

        //noinspection RestrictedApi
        myAdapter.onItemClick = { dish ->
            val intent = Intent(this, DiscoverActivity::class.java)
            intent.putExtra("dish", dish)
            startActivity(intent)
        }

        menuRecycler_view.adapter = myAdapter

        //This brings the extra from the previous activity and deserialize it
        val bundle: Bundle? = intent.extras
        restaurant = bundle?.getSerializable("restaurant") as RestaurantModel




//        val dishesDBHelper = DishesDBHelper(this)
//        dishesDBHelper.deleteTable()
//        dishesDBHelper.readAllDishes()
//
//        val californiaRoll = DishesModel(UUID.randomUUID().toString(), "California roll", "https://www.justonecookbook.com/wp-content/uploads/2014/10/California-Roll.jpg",
//            "cucumber, crab, and avocado", "12")
//        dishesDBHelper.insertDish(californiaRoll)
//
//        val spiderRoll = DishesModel(UUID.randomUUID().toString(), "Spider roll", "https://leesushimiami.com/wp-content/uploads/2018/03/85.-Spider-Roll.jpeg",
//        "battered soft-shell crab, cucumber, avocado, daikon sprouts or lettuce, and spicy mayonnaise, rolled inside nori and sushi rice.", "15")
//
//        dishesDBHelper.insertDish(spiderRoll)
//
//        val spicytuna = DishesModel(UUID.randomUUID().toString(), "Spicy tuna roll", "https://tiger-corporation-us.com/wp-content/uploads/2019/08/spicy-tuna-roll-900x600.jpg",
//        "Sushi rice (steamed rice seasoned with sushi vinegar), nori (seaweed), and sashimi-grade tuna", "8")
//        dishesDBHelper.insertDish((spicytuna))

        getMenu()
//
//        val restaurantMenu = MenuModel(UUID.randomUUID().toString(), restaurant.id, "bead9e9c-dfc3-458f-b1ce-beefd47025f8")
//        val restaurantMenu2 = MenuModel(UUID.randomUUID().toString(), restaurant.id, "bcacfe79-6d6d-4451-ba40-892b271b6bde")
//        val restaurantMenu3 = MenuModel(UUID.randomUUID().toString(), restaurant.id, "e6a46034-79fb-4adc-bcee-14073aecf19d")
//
//        menuDBHelper.insertMenu(restaurantMenu)
//        menuDBHelper.insertMenu(restaurantMenu2)
//        menuDBHelper.insertMenu(restaurantMenu3)
    }


    fun getMenu() {
        val menuList = menuDBHelper.readAllMenu(restaurant.id)
        this.dishesList.addAll(menuList)
    }

}