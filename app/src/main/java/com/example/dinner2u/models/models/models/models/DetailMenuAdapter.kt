package com.example.dinner2u.models.models.models.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.dinner2u.R
import com.example.dinner2u.models.models.models.database.categories.CategoryModel
import com.example.dinner2u.models.models.models.database.dishes.DishesModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_discover.*

class DetailMenuAdapter(private val menuList: ArrayList<DishesModel>):
    RecyclerView.Adapter<DetailMenuAdapter.MyViewHolder>() {

        var onItemClick:((DishesModel) -> Unit)? = null

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): DetailMenuAdapter.MyViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_menu_list_item, parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: DetailMenuAdapter.MyViewHolder, position: Int) {
            val dish: DishesModel = menuList[position]
            holder.name.text = dish.name
            Picasso.with(holder.image.context).load(dish.picture).into(holder.image)
            holder.cardview.setOnClickListener {
                onItemClick?.invoke(dish)
            }
        }

        override fun getItemCount(): Int {
            return menuList.size
        }


        class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val name: TextView = itemView.findViewById(R.id.dish_name)
            val image: ImageView = itemView.findViewById(R.id.dish_image)
            val cardview: CardView = itemView.findViewById(R.id.menuCardView)
        }
}