package com.example.dinner2u.models.models.models.database.dishes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.dinner2u.models.models.models.database.DBContract
import com.example.dinner2u.models.models.models.database.categories.CategoriesDBHelper
import com.example.dinner2u.models.models.models.database.categories.CategoryModel

class DishesDBHelper(context: Context): SQLiteOpenHelper(context, DishesDBHelper.DATABASE_NAME, null, DishesDBHelper.DATABASE_VERSION) {

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
    fun insertDish(dish: DishesModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.DishEntry.COLUMN_DISH_ID, dish.id)
        values.put(DBContract.DishEntry.COLUMN_DISH_NAME, dish.name)

        // Insert the new row, returning the primary key value of the new row

        val newRowId = db.insert(DBContract.DishEntry.TABLE_NAME, null, values)

        return true
    }


    fun readDish(dishid: String): ArrayList<DishesModel> {
        val dishes = ArrayList<DishesModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.DishEntry.TABLE_NAME + " WHERE " + DBContract.DishEntry.COLUMN_DISH_ID + "='" + dishid + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name: String
        var picture: String
        var descrption: String
        var price: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
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
    fun deleteDish(dishid: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.DishEntry.COLUMN_DISH_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(dishid)
        // Issue SQL statement.
        db.delete(DBContract.DishEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }


    fun readAllDishes(): ArrayList<DishesModel> {
        val dishes = ArrayList<DishesModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.DishEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var dishid: String
        var name: String
        var picture: String
        var description: String
        var price: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name = cursor.getString(cursor.getColumnIndex(DBContract.DishEntry.COLUMN_DISH_NAME))
                dishid = cursor.getString(cursor.getColumnIndex(DBContract.DishEntry.COLUMN_DISH_ID))
                picture = cursor.getString(cursor.getColumnIndex(DBContract.DishEntry.COLUMN_DISH_PICTURE))
                description = cursor.getString(cursor.getColumnIndex(DBContract.DishEntry.COLUMN_DISH_DESCRIPTION))
                price = cursor.getString(cursor.getColumnIndex(DBContract.DishEntry.COLUMN_DISH_PRICE))

                dishes.add(DishesModel(dishid, name, picture, description, price))
                cursor.moveToNext()
            }
        }
        return dishes
    }


    companion object{
        val DATABASE_NAME = "dinner2u.db"
        val DATABASE_VERSION: Int = 1

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.DishEntry.TABLE_NAME + " (" +
                    DBContract.DishEntry.COLUMN_DISH_ID + " TEXT PRIMARY KEY," +
                    DBContract.DishEntry.COLUMN_DISH_NAME + " TEXT)"
    }
}