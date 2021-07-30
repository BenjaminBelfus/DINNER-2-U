package com.example.dinner2u.models.models.models.database.categories

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.dinner2u.models.models.models.database.DBContract


class CategoriesDBHelper(context: Context) : SQLiteOpenHelper(context, CategoriesDBHelper.DATABASE_NAME, null, CategoriesDBHelper.DATABASE_VERSION) {

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
    fun insertCategory(category: CategoryModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.CategoryEntry.COLUMN_CATEGORY_ID, category.id)
        values.put(DBContract.CategoryEntry.COLUMN_CATEGORY_NAME, category.name)

        // Insert the new row, returning the primary key value of the new row

        val newRowId = db.insert(DBContract.CategoryEntry.TABLE_NAME, null, values)

        return true
    }


    fun readCategory(categoryid: String): ArrayList<CategoryModel> {
        val categories = ArrayList<CategoryModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.CategoryEntry.TABLE_NAME + " WHERE " + DBContract.CategoryEntry.COLUMN_CATEGORY_ID + "='" + categoryid + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name = cursor.getString(cursor.getColumnIndex(DBContract.CategoryEntry.COLUMN_CATEGORY_NAME))

                categories.add(CategoryModel(categoryid, name))
                cursor.moveToNext()
            }
        }
        return categories
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteCategory(categoryid: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.CategoryEntry.COLUMN_CATEGORY_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(categoryid)
        // Issue SQL statement.
        db.delete(DBContract.CategoryEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }


    fun readAllCategories(): ArrayList<CategoryModel> {
        val categories = ArrayList<CategoryModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.CategoryEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var categoryid: String
        var nameid: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                categoryid = cursor.getString(cursor.getColumnIndex(DBContract.CategoryEntry.COLUMN_CATEGORY_ID))
                nameid = cursor.getString(cursor.getColumnIndex(DBContract.CategoryEntry.COLUMN_CATEGORY_NAME))

                categories.add(CategoryModel(categoryid, nameid))
                cursor.moveToNext()
            }
        }
        return categories
    }


    companion object{
        val DATABASE_NAME = "dinner2u.db"
        val DATABASE_VERSION: Int = 1

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.CategoryEntry.TABLE_NAME + " (" +
                    DBContract.CategoryEntry.COLUMN_CATEGORY_ID + " TEXT PRIMARY KEY," +
                    DBContract.CategoryEntry.COLUMN_CATEGORY_NAME + " TEXT)"
    }
}