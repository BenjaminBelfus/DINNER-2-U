package com.example.dinner2u.models.models.models.adapters

import android.provider.BaseColumns

object DBContract {

    class UserEntry: BaseColumns {
        companion object {
            val TABLE_NAME = "users"
            val COLUMN_USER_ID = "userid"
            val COLUMN_NAME = "name"
            val COLUMN_LASTNAME = "lastname"
            val COLUMN_EMAIL = "email"
            val COLUMN_PASSWORD = "password"
            val COLUMN_DATE_CREATED = "datecreated"
            val COLUMN_MAIN_ADDRESS = "mainadress"
        }
    }
}