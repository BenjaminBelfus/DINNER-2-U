package com.example.dinner2u.models.models.models.datasource

import com.example.dinner2u.models.models.models.dataclass.RestaurantPost
import com.example.dinner2u.models.models.models.dataclass.UserDataClass

class UserDataSource {
    companion object{
        val temporalHashmap:HashMap<String,ArrayList<String>> = HashMap<String, ArrayList<String>>()

        fun createDataSet() {
            val list1 = ArrayList<String>()
            list1[0] = "Benjamin"
            list1[1] = "Belfus"
            list1[2] = "benjaminBelfus@hotmail.com"
            list1[3] = "6/18/2021"
            list1[4] = "2525 Benvenue Ave"

            temporalHashmap.put("benjaminbelfus@hotmail.com", list1)

            UserDataClass(
                temporalHashmap
            )

            val list2 = ArrayList<String>()
            list2[0] = "Roger"
            list2[1] = "Arroyo"
            list2[2] = "ra@hotmail.com"
            list2[3] = "6/18/2021"
            list2[4] = "Lima, Peru"

            temporalHashmap.put("ra@hotmail.com", list2)

            UserDataClass(
                temporalHashmap
            )
        }
    }
}