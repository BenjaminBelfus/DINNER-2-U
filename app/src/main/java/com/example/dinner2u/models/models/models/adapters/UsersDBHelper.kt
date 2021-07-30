package com.example.dinner2u.models.models.models.adapters

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper


class UsersDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
    fun insertUser(user: UserModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.UserEntry.COLUMN_USER_ID, user.id)
        values.put(DBContract.UserEntry.COLUMN_NAME, user.name)
        values.put(DBContract.UserEntry.COLUMN_LASTNAME, user.lastname)
        values.put(DBContract.UserEntry.COLUMN_EMAIL, user.email)
        values.put(DBContract.UserEntry.COLUMN_PASSWORD, user.password)
        values.put(DBContract.UserEntry.COLUMN_DATE_CREATED, user.datecreated)
        values.put(DBContract.UserEntry.COLUMN_MAIN_ADDRESS, user.mainaddress)

        // Insert the new row, returning the primary key value of the new row

        val newRowId = db.insert(DBContract.UserEntry.TABLE_NAME, null, values)

        return true
    }


    fun readUser(userid: String): ArrayList<UserModel> {
        val users = ArrayList<UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_USER_ID + "='" + userid + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name: String
        var lastname: String
        var email: String
        var password: String
        var datecreated: String
        var mainaddress: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME))
                lastname = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_LASTNAME))
                email = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_EMAIL))
                password = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_PASSWORD))
                datecreated = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATE_CREATED))
                mainaddress = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_MAIN_ADDRESS))

                users.add(UserModel(userid, name, lastname, email, password, datecreated, mainaddress ))
                cursor.moveToNext()
            }
        }
        return users
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(userid: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.UserEntry.COLUMN_USER_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(userid)
        // Issue SQL statement.
        db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }


    @Throws(SQLiteConstraintException::class)
    fun getUser(email: String): UserModel? {
        val users = ArrayList<UserModel>()
        // Gets the data repository in write mode
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_EMAIL + "='" + email + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return null
        }

        var userid: String
        var name: String
        var lastname: String
        var email: String
        var password: String
        var datecreated: String
        var mainaddress: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                userid = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_USER_ID))
                name = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME))
                lastname = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_LASTNAME))
                email = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_EMAIL))
                password = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_PASSWORD))
                datecreated = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_DATE_CREATED))
                mainaddress = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_MAIN_ADDRESS))

                users.add(UserModel(userid, name, lastname, email, password, datecreated, mainaddress ))
                cursor.moveToNext()
            }
        }
        if (users.size == 0) {
            return null
        } else {
            return users.first()
        }
    }




    companion object{
        val DATABASE_NAME = "dinner2u.db"
        val DATABASE_VERSION: Int = 1

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + " (" +
                    DBContract.UserEntry.COLUMN_USER_ID + " TEXT PRIMARY KEY," +
                    DBContract.UserEntry.COLUMN_NAME + " TEXT," +
                    DBContract.UserEntry.COLUMN_LASTNAME + " TEXT," +
                    DBContract.UserEntry.COLUMN_EMAIL + " TEXT," +
                    DBContract.UserEntry.COLUMN_PASSWORD + " TEXT," +
                    DBContract.UserEntry.COLUMN_DATE_CREATED + " TEXT," +
                    DBContract.UserEntry.COLUMN_MAIN_ADDRESS + " TEXT)"
    }

}