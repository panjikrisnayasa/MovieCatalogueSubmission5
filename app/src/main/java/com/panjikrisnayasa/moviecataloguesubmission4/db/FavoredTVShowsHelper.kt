package com.panjikrisnayasa.moviecataloguesubmission4.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.sql.SQLException

class FavoredTVShowsHelper(context: Context) {
    private val mDatabaseFavoredTVShowsHelper = DatabaseFavoredTVShowsHelper(context)
    private lateinit var mDatabase: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE =
            DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.TABLE_NAME
        private var INSTANCE: FavoredTVShowsHelper? = null

        fun getInstance(context: Context): FavoredTVShowsHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = FavoredTVShowsHelper(context)
                    }
                }
            }
            return INSTANCE as FavoredTVShowsHelper
        }
    }

    @Throws(SQLException::class)
    fun open() {
        mDatabase = mDatabaseFavoredTVShowsHelper.writableDatabase
    }

    fun close() {
        mDatabaseFavoredTVShowsHelper.close()

        if (mDatabase.isOpen) {
            mDatabase.close()
        }
    }

    fun queryAll(): Cursor {
        return mDatabase.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "${DatabaseFavoredTVShowsContract.FavoredTVShowsColumns._ID} ASC"
        )
    }

    fun queryByName(name: String): Cursor {
        return mDatabase.query(
            DATABASE_TABLE,
            null,
            "${DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.NAME} = ?",
            arrayOf(name),
            null,
            null,
            null,
            null
        )
    }

    fun insert(values: ContentValues?): Long {
        return mDatabase.insert(DATABASE_TABLE, null, values)
    }

    fun deleteById(id: String): Int {
        return mDatabase.delete(
            DATABASE_TABLE,
            "${DatabaseFavoredTVShowsContract.FavoredTVShowsColumns._ID} = '$id'",
            null
        )
    }
}