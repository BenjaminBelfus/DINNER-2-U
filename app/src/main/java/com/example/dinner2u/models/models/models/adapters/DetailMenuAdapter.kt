package com.example.dinner2u.models.models.models.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dinner2u.R
import com.example.dinner2u.models.models.models.dataclass.DishesDataClass
import com.example.dinner2u.models.models.models.dataclass.RestaurantMenu
import com.example.dinner2u.models.models.models.dataclass.RestaurantPost
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_menu.view.*
import kotlinx.android.synthetic.main.activity_dish_detail.*
import kotlinx.android.synthetic.main.layout_menu_list_item.view.*
import java.util.HashMap

class DetailMenuAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var restaurantID:Int = 0
    var dataclass:DishesDataClass = DishesDataClass()
    private var items: List<RestaurantMenu> = ArrayList()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MenuViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_menu_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MenuViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        var counter:Int = 0
        if (dataclass.userHashmap.get(restaurantID) != null) {
            for ((key, value) in dataclass.userHashmap) {
                counter++
            }
        }
        return counter
    }

    fun submitList(menuList: List<RestaurantMenu>){
        items = menuList
    }



    class MenuViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        val img :ImageView = itemView.dish_image
        val name :TextView = itemView.dish_name

        fun bind(resMenu: RestaurantMenu) {
            name.setText(resMenu.dishName)

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .load(resMenu.dishImage)
                .into(img)
        }

    }



}