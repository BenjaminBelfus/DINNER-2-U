package com.example.dinner2u.models.models.models.database.dishes

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DishesModel (

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("picture")
    val picture: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: String
    ):Serializable

