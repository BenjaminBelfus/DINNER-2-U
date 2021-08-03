package com.example.dinner2u.models.models.models.database.restaurants

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.dinner2u.models.models.models.database.DBContract
import com.example.dinner2u.models.models.models.database.users.UsersDBHelper

class RestaurantsDBHelper(context: Context) : SQLiteOpenHelper(context,
    UsersDBHelper.DATABASE_NAME, null,
    UsersDBHelper.DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL(SQL_CREATE_ENTRIES)
            onCreate(db)
        }
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertRestaurant(restaurant: RestaurantModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.RestaurantEntry.COLUMN_RESTAURANT_ID, restaurant.id)
        values.put(DBContract.RestaurantEntry.COLUMN_RESTAURANT_NAME, restaurant.name)
        values.put(DBContract.RestaurantEntry.COLUMN_RESTAURANT_DESCRIPTION, restaurant.description)
        values.put(DBContract.RestaurantEntry.COLUMN_RESTAURANT_FOODCATEGORYID, restaurant.foodcategoryID)
        values.put(DBContract.RestaurantEntry.COLUMN_RESTAURANT_MAINPICTURE, restaurant.mainpicture)
        values.put(DBContract.RestaurantEntry.COLUMN_RESTAURANT_FIRSTPICTURE, restaurant.firstpicture)
        values.put(DBContract.RestaurantEntry.COLUMN_RESTAURANT_SECONDPICTURE, restaurant.secondpicture)
        values.put(DBContract.RestaurantEntry.COLUMN_RESTAURANT_THIRDPICTURE, restaurant.thirdpicture)

        // Insert the new row, returning the primary key value of the new row

        val newRowId = db.insert(DBContract.RestaurantEntry.TABLE_NAME, null, values)

        return true
    }


    fun readRestaurant(restaurantid: String): ArrayList<RestaurantModel> {
        val restaurants = ArrayList<RestaurantModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.RestaurantEntry.TABLE_NAME + " WHERE " + DBContract.RestaurantEntry.COLUMN_RESTAURANT_ID + "='" + restaurantid + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name: String
        var description: String
        var foodcategory: String
        var mainpicture: String
        var firstpicture: String
        var secondpicture: String
        var thirdpicture: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_NAME))
                description = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_DESCRIPTION))
                foodcategory = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_FOODCATEGORYID))
                mainpicture = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_MAINPICTURE))
                firstpicture = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_FIRSTPICTURE))
                secondpicture = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_SECONDPICTURE))
                thirdpicture = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_THIRDPICTURE))

                restaurants.add(RestaurantModel(restaurantid, name, description, foodcategory, mainpicture, firstpicture, secondpicture, thirdpicture))
                cursor.moveToNext()
            }
        }
        return restaurants
    }


    fun readAllRestaurantsFromCategory(categoryid: String): ArrayList<RestaurantModel> {
        val restaurants = ArrayList<RestaurantModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.RestaurantEntry.TABLE_NAME + " WHERE "
                    + DBContract.RestaurantEntry.COLUMN_RESTAURANT_FOODCATEGORYID + " = '" + categoryid + "'"  , null)
        } catch (e: SQLiteException) {
            db.execSQL(RestaurantsDBHelper.SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var restaurantid: String
        var name: String
        var description: String
        var foodcategory: String
        var mainpicture: String
        var firstpicture: String
        var secondpicture: String
        var thirdpicture: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                restaurantid = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_ID))
                name = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_NAME))
                description = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_DESCRIPTION))
                foodcategory = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_FOODCATEGORYID))
                mainpicture = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_MAINPICTURE))
                firstpicture = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_FIRSTPICTURE))
                secondpicture = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_SECONDPICTURE))
                thirdpicture = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_THIRDPICTURE))

                restaurants.add(RestaurantModel(restaurantid, name, description, foodcategory, mainpicture, firstpicture, secondpicture, thirdpicture))
                cursor.moveToNext()
            }
        }
        return restaurants
    }

    fun readAllRestaurantForCategoryDiscover(): ArrayList<RestaurantModel> {
        val restaurants = ArrayList<RestaurantModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.RestaurantEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(RestaurantsDBHelper.SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var restaurantid: String
        var name: String
        var description: String
        var foodcategory: String
        var mainpicture: String
        var firstpicture: String
        var secondpicture: String
        var thirdpicture: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                restaurantid = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_ID))
                name = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_NAME))
                description = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_DESCRIPTION))
                foodcategory = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_FOODCATEGORYID))
                mainpicture = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_MAINPICTURE))
                firstpicture = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_FIRSTPICTURE))
                secondpicture = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_SECONDPICTURE))
                thirdpicture = cursor.getString(cursor.getColumnIndex(DBContract.RestaurantEntry.COLUMN_RESTAURANT_THIRDPICTURE))

                restaurants.add(RestaurantModel(restaurantid, name, description, foodcategory, mainpicture, firstpicture, secondpicture, thirdpicture))
                cursor.moveToNext()
            }
        }
        return restaurants
    }


    @Throws(SQLiteConstraintException::class)
    fun deleteRestaurant(restaurantid: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.RestaurantEntry.COLUMN_RESTAURANT_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(restaurantid)
        // Issue SQL statement.
        db.delete(DBContract.RestaurantEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }


    companion object{
        val DATABASE_NAME = "dinner2u.db"
        val DATABASE_VERSION: Int = 1

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.RestaurantEntry.TABLE_NAME + " (" +
                    DBContract.RestaurantEntry.COLUMN_RESTAURANT_ID + " TEXT PRIMARY KEY," +
                    DBContract.RestaurantEntry.COLUMN_RESTAURANT_NAME + " TEXT," +
                    DBContract.RestaurantEntry.COLUMN_RESTAURANT_DESCRIPTION + " TEXT," +
                    DBContract.RestaurantEntry.COLUMN_RESTAURANT_FOODCATEGORYID + " TEXT," +
                    DBContract.RestaurantEntry.COLUMN_RESTAURANT_MAINPICTURE + " TEXT," +
                    DBContract.RestaurantEntry.COLUMN_RESTAURANT_FIRSTPICTURE + " TEXT," +
                    DBContract.RestaurantEntry.COLUMN_RESTAURANT_SECONDPICTURE + " TEXT," +
                    DBContract.RestaurantEntry.COLUMN_RESTAURANT_THIRDPICTURE + " TEXT)"
    }
}