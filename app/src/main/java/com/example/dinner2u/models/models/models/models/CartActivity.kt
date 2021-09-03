package com.example.dinner2u.models.models.models.models

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dinner2u.R
import com.example.dinner2u.models.models.models.database.cart.CartDBHelper
import com.example.dinner2u.models.models.models.database.cart.CartItemModel
import com.example.dinner2u.models.models.models.models.adapters.CartAdapter
import com.example.dinner2u.models.models.models.models.managers.DataManager
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.layout_cart_list_item.view.*

class CartActivity : AppCompatActivity() {

    lateinit var cartDBHelper: CartDBHelper
    lateinit var myAdapter: CartAdapter

    val cartItemList: ArrayList<CartItemModel> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        cartRecyclerView.setHasFixedSize(true)


        myAdapter = CartAdapter(cartItemList)

        cartDBHelper = CartDBHelper(this)

        //Recyclerview
        cartRecyclerView.adapter = myAdapter

        //cartRecyclerView.isNestedScrollingEnabled = false

        getCartItems()

    }

    fun getCartItems() {
        var cartItems = cartDBHelper.readAllItemsCart(DataManager.getCurrentUserID())
        cartItemList.addAll(cartItems)
        updateExtraData()
    }

    @SuppressLint("SetTextI18n")
    fun updateExtraData() {
        if (cartItemList.size > 0) {
            cartRestaurantName.text = cartItemList.first().restaurantname + " & " //+ cartItemList[3].restaurantname
            var total:Int = 0
            for (item in cartItemList) {
                total = total + item.dishprice.toInt()
            }
            cartTotalPriceID.text = "Total Price: $" + total
            cartAddressID.text = "Address: " + cartItemList.first().useradress

        }
    }
}