package com.example.dinner2u.models.models.models.database.categories

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoryModel (

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
) : Serializable
