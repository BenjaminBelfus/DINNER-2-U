package com.example.dinner2u.models.models.models.database.users

data class UserModel(
    val id:String,
    val name:String,
    val lastname:String,
    val email:String,
    val password:String,
    val datecreated:String,
    val mainaddress:String
)
