package com.panjikrisnayasa.moviecataloguesubmission4.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.panjikrisnayasa.moviecataloguesubmission4.db.DatabaseFavoredMoviesContract.FavoredMoviesColumns.Companion.TABLE_NAME
import com.panjikrisnayasa.moviecataloguesubmission4.db.DatabaseFavoredMoviesContract.FavoredMoviesColumns.Companion.TITLE
import com.panjikrisnayasa.moviecataloguesubmission4.db.DatabaseFavoredMoviesContract.FavoredMoviesColumns.Companion._ID
import java.sql.SQLException

class FavoredMoviesHelper(context: Context) {
    private val mDatabaseFavoredMoviesHelper = DatabaseFavoredMoviesHelper(context)
    private lateinit var mDatabase: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: FavoredMoviesHelper? = null

        fun getInstance(context: Context): FavoredMoviesHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = FavoredMoviesHelper(context)
                    }
                }
            }
            return INSTANCE as FavoredMoviesHelper
        }
    }

    @Throws(SQLException::class)
    fun open() {
        mDatabase = mDatabaseFavoredMoviesHelper.writableDatabase
    }

    fun close() {
        mDatabaseFavoredMoviesHelper.close()

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
            "$_ID ASC"
        )
    }

    fun queryByTitle(title: String): Cursor {
        return mDatabase.query(
            DATABASE_TABLE,
            null,
            "$TITLE = ?",
            arrayOf(title),
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
        return mDatabase.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }
}