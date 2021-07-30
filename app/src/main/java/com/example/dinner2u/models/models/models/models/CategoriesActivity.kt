package com.example.dinner2u.models.models.models.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dinner2u.R
import com.example.dinner2u.models.models.models.database.categories.CategoriesDBHelper
import com.example.dinner2u.models.models.models.database.categories.CategoryModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_categories.*
import java.util.*
import kotlin.collections.ArrayList

class CategoriesActivity : AppCompatActivity() {

    private lateinit var categoryList: ArrayList<CategoryModel>
    private lateinit var myAdapter: CategoryAdapter
    private lateinit var categoryDBHelper: CategoriesDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        categoriesRecyclerView.layoutManager = LinearLayoutManager(this)
        categoriesRecyclerView.setHasFixedSize(true)
        categoryList = arrayListOf<CategoryModel>()
        myAdapter = CategoryAdapter(categoryList)
        categoriesRecyclerView.adapter = myAdapter
        categoryDBHelper = CategoriesDBHelper(this)

        getCategories()

    }

    fun getCategories() {
        var categories = categoryDBHelper.readAllCategories()
        categoryList.addAll(categories)
//        categoryDBHelper.insertCategory(CategoryModel(UUID.randomUUID().toString(), "Sushi"))
    }



}