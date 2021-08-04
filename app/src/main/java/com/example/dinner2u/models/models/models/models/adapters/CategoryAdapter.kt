package com.example.dinner2u.models.models.models.models.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.dinner2u.R
import com.example.dinner2u.models.models.models.database.categories.CategoryModel

class CategoryAdapter(private val categoryList: ArrayList<CategoryModel>):
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    var onItemClick:((CategoryModel) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_category_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val category: CategoryModel = categoryList[position]
        holder.name.text = category.name
        holder.cardview.setOnClickListener {
            onItemClick?.invoke(category)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.nameCategoryTextView)
        val cardview: CardView = itemView.findViewById(R.id.categoryCardID)
    }
}