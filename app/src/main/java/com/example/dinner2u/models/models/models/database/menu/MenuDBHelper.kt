package com.example.dinner2u.models.models.models.database.menu

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.dinner2u.models.models.models.database.DBContract
import com.example.dinner2u.models.models.models.database.dishes.DishesModel
import com.example.dinner2u.models.models.models.database.restaurants.RestaurantModel
import com.example.dinner2u.models.models.models.database.restaurants.RestaurantsDBHelper
import com.example.dinner2u.models.models.models.database.users.UsersDBHelper

class MenuDBHelper(context: Context) : SQLiteOpenHelper(context,
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
    fun insertMenu(menu: MenuModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.MenuEntry.COLUMN_MENU_ID, menu.menuid)
        values.put(DBContract.MenuEntry.COLUMN_RESTAURANT_ID, menu.restaurantid)
        values.put(DBContract.MenuEntry.COLUMN_DISH_ID, menu.dishid)



        // Insert the new row, returning the primary key value of the new row

        val newRowId = db.insert(DBContract.MenuEntry.TABLE_NAME, null, values)

        return true
    }


    fun readMenu(menuid: String): ArrayList<MenuModel> {
        val menus = ArrayList<MenuModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.MenuEntry.TABLE_NAME + " WHERE " + DBContract.MenuEntry.COLUMN_MENU_ID + "='" + menuid + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var menuid: String
        var restaurantid: String
        var dishid: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                menuid = cursor.getString(cursor.getColumnIndex(DBContract.MenuEntry.COLUMN_MENU_ID))
                restaurantid = cursor.getString(cursor.getColumnIndex(DBContract.MenuEntry.COLUMN_RESTAURANT_ID))
                dishid = cursor.getString(cursor.getColumnIndex(DBContract.MenuEntry.COLUMN_DISH_ID))

                menus.add(MenuModel(menuid, restaurantid, dishid))
                cursor.moveToNext()
            }
        }
        return menus
    }


    fun readAllMenu(restaurantid: String): ArrayList<DishesModel> {
        val dishes = ArrayList<DishesModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select " + DBContract.MenuEntry.TABLE_NAME + "." + DBContract.DishEntry.COLUMN_DISH_ID + ", " + DBContract.DishEntry.COLUMN_DISH_NAME + ", " +
                    DBContract.DishEntry.COLUMN_DISH_PICTURE + ", " + DBContract.DishEntry.COLUMN_DISH_DESCRIPTION + ", " + DBContract.DishEntry.COLUMN_DISH_PRICE +
                " from " + DBContract.MenuEntry.TABLE_NAME + " LEFT JOIN " + DBContract.DishEntry.TABLE_NAME + " ON " + DBContract.MenuEntry.TABLE_NAME + "."
                    + DBContract.MenuEntry.COLUMN_DISH_ID + " = " + DBContract.DishEntry.TABLE_NAME + "."
                    + DBContract.DishEntry.COLUMN_DISH_ID
                    + " WHERE " + DBContract.MenuEntry.COLUMN_RESTAURANT_ID + " = '" + restaurantid + "'"  , null)
        } catch (e: SQLiteException) {
            db.execSQL(MenuDBHelper.SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var dishid: String
        var name: String
        var picture: String
        var descrption: String
        var price: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                dishid = cursor.getString(cursor.getColumnIndex(DBContract.DishEntry.COLUMN_DISH_ID))
                name = cursor.getString(cursor.getColumnIndex(DBContract.DishEntry.COLUMN_DISH_NAME))
                picture = cursor.getString(cursor.getColumnIndex(DBContract.DishEntry.COLUMN_DISH_PICTURE))
                descrption = cursor.getString(cursor.getColumnIndex(DBContract.DishEntry.COLUMN_DISH_DESCRIPTION))
                price = cursor.getString(cursor.getColumnIndex(DBContract.DishEntry.COLUMN_DISH_PRICE))

                dishes.add(DishesModel(dishid, name, picture, descrption, price))
                cursor.moveToNext()
            }
        }
        return dishes
    }



    @Throws(SQLiteConstraintException::class)
    fun deleteMenu(menuid: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.MenuEntry.COLUMN_MENU_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(menuid)
        // Issue SQL statement.
        db.delete(DBContract.MenuEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }


    companion object{
        val DATABASE_NAME = "dinner2u.db"
        val DATABASE_VERSION: Int = 1

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.MenuEntry.TABLE_NAME + " (" +
                    DBContract.MenuEntry.COLUMN_MENU_ID + " TEXT PRIMARY KEY," +
                    DBContract.MenuEntry.COLUMN_RESTAURANT_ID + " TEXT," +
                    DBContract.MenuEntry.COLUMN_DISH_ID + " TEXT)"
    }
}