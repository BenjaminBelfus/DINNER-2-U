package com.example.dinner2u.models.models.models.database.cart

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.dinner2u.models.models.models.database.DBContract
import com.example.dinner2u.models.models.models.database.categories.CategoryModel

class CartDBHelper(context: Context) : SQLiteOpenHelper(context, CartDBHelper.DATABASE_NAME, null, CartDBHelper.DATABASE_VERSION) {

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
    fun insertCart(cart: CartModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.CartEntry.COLUMN_CART_ID, cart.id)
        values.put(DBContract.CartEntry.COLUMN_RESTAURANT_ID, cart.restaurantid)
        values.put(DBContract.CartEntry.COLUMN_DISH_ID, cart.dishid)
        values.put(DBContract.CartEntry.COLUMN_USER_ID, cart.userid)


        // Insert the new row, returning the primary key value of the new row

        val newRowId = db.insert(DBContract.CartEntry.TABLE_NAME, null, values)

        return true
    }


    fun readCart(cartid: String): ArrayList<CartModel> {
        val cart = ArrayList<CartModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.CartEntry.TABLE_NAME + " WHERE " + DBContract.CartEntry.COLUMN_CART_ID + "='" + cartid + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var restaurantid: String
        var dishid: String
        var userid: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                restaurantid = cursor.getString(cursor.getColumnIndex(DBContract.CartEntry.COLUMN_RESTAURANT_ID))
                dishid = cursor.getString(cursor.getColumnIndex(DBContract.CartEntry.COLUMN_DISH_ID))
                userid = cursor.getString(cursor.getColumnIndex(DBContract.CartEntry.COLUMN_USER_ID))

                cart.add(CartModel(cartid, restaurantid, dishid, userid))
                cursor.moveToNext()
            }
        }
        return cart
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteCart(cartid: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.CartEntry.COLUMN_CART_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(cartid)
        // Issue SQL statement.
        db.delete(DBContract.CartEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }


    fun readAllItemsCart(userid: String): ArrayList<CartItemModel> {
        val cartItems = ArrayList<CartItemModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT " + DBContract.CartEntry.COLUMN_CART_ID + " , " +
                    DBContract.RestaurantEntry.COLUMN_RESTAURANT_NAME + " , " +
                    DBContract.DishEntry.COLUMN_DISH_NAME  + " , " +
                    DBContract.DishEntry.COLUMN_DISH_PRICE + " , " +
                    DBContract.UserEntry.COLUMN_MAIN_ADDRESS + " FROM " +
                    DBContract.CartEntry.TABLE_NAME +   " LEFT JOIN " +
                    DBContract.RestaurantEntry.TABLE_NAME +  " ON " +
                    DBContract.CartEntry.TABLE_NAME + "."  + DBContract.CartEntry.COLUMN_RESTAURANT_ID +  " = " +
                    DBContract.RestaurantEntry.TABLE_NAME + "." + DBContract.RestaurantEntry.COLUMN_RESTAURANT_ID + " LEFT JOIN " +
                    DBContract.DishEntry.TABLE_NAME + " ON " +
                    DBContract.CartEntry.TABLE_NAME + "." + DBContract.CartEntry.COLUMN_DISH_ID + " = "  +
                    DBContract.DishEntry.TABLE_NAME + "." + DBContract.DishEntry.COLUMN_DISH_ID+ " LEFT JOIN " +
                    DBContract.UserEntry.TABLE_NAME + " ON " + DBContract.CartEntry.TABLE_NAME + "." + DBContract.CartEntry.COLUMN_USER_ID + " = " +
                    DBContract.UserEntry.TABLE_NAME + "." + DBContract.UserEntry.COLUMN_USER_ID + " WHERE " +
                    DBContract.CartEntry.TABLE_NAME + "." +  DBContract.CartEntry.COLUMN_USER_ID+ " = '" + userid +"'", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var cartid: String
        var restaurantname: String
        var dishname: String
        var dishprice: String
        var useraddress: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                cartid = cursor.getString(cursor.getColumnIndex(DBContract.CartEntry.COLUMN_CART_ID))
                restaurantname = cursor.getString(cursor.getColumnIndex(DBContract.CartItemEntry.COLUMN_RESTAURANT_NAME))
                dishname = cursor.getString(cursor.getColumnIndex(DBContract.CartItemEntry.COLUMN_DISH_NAME))
                dishprice = cursor.getString(cursor.getColumnIndex(DBContract.CartItemEntry.COLUMN_DISH_PRICE))
                useraddress = cursor.getString(cursor.getColumnIndex(DBContract.CartItemEntry.COLUMN_USER_ADDRESS))

                cartItems.add(CartItemModel(cartid, restaurantname, dishname, dishprice, useraddress))
                cursor.moveToNext()
            }
        }
        return cartItems
    }


    companion object{
        val DATABASE_NAME = "dinner2u.db"
        val DATABASE_VERSION: Int = 1

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.CartEntry.TABLE_NAME + " (" +
                    DBContract.CartEntry.COLUMN_CART_ID + " TEXT PRIMARY KEY," +
                    DBContract.CartEntry.COLUMN_RESTAURANT_ID + " TEXT," +
                    DBContract.CartEntry.COLUMN_DISH_ID + " TEXT," +
                    DBContract.CartEntry.COLUMN_USER_ID + " TEXT)"
    }
}