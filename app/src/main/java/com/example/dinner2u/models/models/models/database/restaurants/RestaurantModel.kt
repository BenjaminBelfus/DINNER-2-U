package com.example.dinner2u.models.models.models.database.restaurants

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RestaurantModel(

    @SerializedName("id")
    val id:String,

    @SerializedName("name")
    val name:String,

    @SerializedName("description")
    val description: String,

    @SerializedName("foodcategoryID")
    val foodcategoryID: String,

    @SerializedName("mainpitcure")
    val mainpicture: String,

    @SerializedName("firstpicture")
    val firstpicture: String,

    @SerializedName("secondpicture")
    val secondpicture: String,

    @SerializedName("thirdpicture")
    val thirdpicture: String
):Serializable
