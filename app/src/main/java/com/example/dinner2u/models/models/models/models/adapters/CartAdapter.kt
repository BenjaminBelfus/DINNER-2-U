package com.example.dinner2u.models.models.models.models.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.dinner2u.R
import com.example.dinner2u.models.models.models.database.cart.CartItemModel
import com.example.dinner2u.models.models.models.database.categories.CategoryModel

class CartAdapter (private val cartItemList: ArrayList<CartItemModel>):
    RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    var onItemClick:((CartItemModel) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_cart_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cart: CartItemModel = cartItemList[position]
        holder.dishname.text = cart.dishname
        holder.dishprice.text = cart.dishprice
        holder.cardview.setOnClickListener {
            onItemClick?.invoke(cart)
        }
    }

    override fun getItemCount(): Int {
        return cartItemList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val dishname: TextView = itemView.findViewById(R.id.CartItemDishName)
        val dishprice: TextView = itemView.findViewById(R.id.CartItemDishPrice)
        val cardview: CardView = itemView.findViewById(R.id.CartCardView)
    }
}