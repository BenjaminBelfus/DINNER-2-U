package com.example.dinner2u.models.models.models.models

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dinner2u.R
import com.example.dinner2u.models.models.models.adapters.DetailMenuAdapter
import com.example.dinner2u.models.models.models.datasource.DataSource
import kotlinx.android.synthetic.main.activity_detail_menu.*

class DetailMenuActivity : AppCompatActivity() {
    private lateinit var menuAdapter: DetailMenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu)

        initRecyclerView()
        addDataSet()

    }

    private fun addDataSet() {
        val data = DataSource.createDataSet()
        menuAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this@DetailMenuActivity)
        menuAdapter = DetailMenuAdapter()
        recycler_view.adapter = menuAdapter
    }
}