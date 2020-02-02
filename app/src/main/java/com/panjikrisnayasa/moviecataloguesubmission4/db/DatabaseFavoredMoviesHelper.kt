package com.panjikrisnayasa.moviecataloguesubmission4.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.panjikrisnayasa.moviecataloguesubmission4.db.DatabaseFavoredMoviesContract.FavoredMoviesColumns.Companion.TABLE_NAME

internal class DatabaseFavoredMoviesHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "favored_movies_db"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
                "(${DatabaseFavoredMoviesContract.FavoredMoviesColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${DatabaseFavoredMoviesContract.FavoredMoviesColumns.POSTER_PATH} TEXT NOT NULL, " +
                "${DatabaseFavoredMoviesContract.FavoredMoviesColumns.TITLE} TEXT NOT NULL, " +
                "${DatabaseFavoredMoviesContract.FavoredMoviesColumns.GENRE} TEXT NOT NULL, " +
                "${DatabaseFavoredMoviesContract.FavoredMoviesColumns.POPULARITY} REAL NOT NULL, " +
                "${DatabaseFavoredMoviesContract.FavoredMoviesColumns.RELEASE_DATE} TEXT NOT NULL, " +
                "${DatabaseFavoredMoviesContract.FavoredMoviesColumns.RATING} TEXT NOT NULL, " +
                "${DatabaseFavoredMoviesContract.FavoredMoviesColumns.VOTE_AVERAGE} REAL NOT NULL, " +
                "${DatabaseFavoredMoviesContract.FavoredMoviesColumns.OVERVIEW} TEXT NOT NULL)"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val sql = "DROP TABLE IF EXISTS $TABLE_NAME"
        p0?.execSQL(sql)
        onCreate(p0)
    }
}