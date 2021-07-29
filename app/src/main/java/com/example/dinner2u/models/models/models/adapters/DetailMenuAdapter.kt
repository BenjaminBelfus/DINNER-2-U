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
import kotlinx.android.synthetic.main.activity_detail_menu.view.*
import kotlinx.android.synthetic.main.layout_menu_list_item.view.*

class DetailMenuAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var restaurantID:Int = 0
    var dataclass:DishesDataClass = DishesDataClass()


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
        return items.size
    }

    fun submitList(menuList: List<RestaurantMenu>){
        items = menuList
    }



    class MenuViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {

    }



}