package com.example.dinner2u.models.models.models.models

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dinner2u.R
import com.example.dinner2u.models.models.models.database.categories.CategoriesDBHelper
import com.example.dinner2u.models.models.models.database.categories.CategoryModel
import kotlinx.android.synthetic.main.activity_categories.*
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


        //noinspection RestrictedApi
        myAdapter.onItemClick = { category ->
            val intent = Intent(this, DiscoverActivity::class.java)
            intent.putExtra("category", category)
            startActivity(intent)
        }


        categoriesRecyclerView.adapter = myAdapter
        categoryDBHelper = CategoriesDBHelper(this)

        getCategories()

        logoutButton.setOnClickListener{
            logOutAlert()
        }

    }

    fun getCategories() {
        var categories = categoryDBHelper.readAllCategories()
        categoryList.addAll(categories)
//        categoryDBHelper.insertCategory(CategoryModel(UUID.randomUUID().toString(), "Discover"))
//        categoryDBHelper.insertCategory(CategoryModel(UUID.randomUUID().toString(), "Sushi"))
//        categoryDBHelper.insertCategory(CategoryModel(UUID.randomUUID().toString(), "Pizza"))
//        categoryDBHelper.insertCategory(CategoryModel(UUID.randomUUID().toString(), "Burguers"))
//        categoryDBHelper.insertCategory(CategoryModel(UUID.randomUUID().toString(), "Breakfast"))
//        categoryDBHelper.insertCategory(CategoryModel(UUID.randomUUID().toString(), "Pasta"))
//        categoryDBHelper.insertCategory(CategoryModel(UUID.randomUUID().toString(), "Chinese"))
//        categoryDBHelper.insertCategory(CategoryModel(UUID.randomUUID().toString(), "Salads"))
//        categoryDBHelper.insertCategory(CategoryModel(UUID.randomUUID().toString(), "Sandwich"))
//        categoryDBHelper.insertCategory(CategoryModel(UUID.randomUUID().toString(), "Japanese"))
//        categoryDBHelper.insertCategory(CategoryModel(UUID.randomUUID().toString(), "Ramen"))
//        categoryDBHelper.insertCategory(CategoryModel(UUID.randomUUID().toString(), "Vegan"))
//        categoryDBHelper.insertCategory(CategoryModel(UUID.randomUUID().toString(), "Burrito"))
    }

    fun logOutAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Alert")
        builder.setMessage("Are you sure you want to logout")
        builder.setPositiveButton("OKEY", DialogInterface.OnClickListener(function = logOutbuttonClick))
        builder.setNegativeButton("CANCEL", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
        }

    val logOutbuttonClick = {
        dialog:DialogInterface, which: Int ->
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}