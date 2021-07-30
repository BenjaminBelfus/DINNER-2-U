package com.example.dinner2u.models.models.models.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dinner2u.R
import com.example.dinner2u.models.models.models.database.categories.CategoryModel

class CategoryAdapter(private val categoryList: ArrayList<CategoryModel>):
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_category_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.MyViewHolder, position: Int) {
        val category: CategoryModel = categoryList[position]
        holder.name.text = category.name
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.nameCategoryTextView)
    }
}