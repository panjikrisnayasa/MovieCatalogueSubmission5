package com.panjikrisnayasa.moviecataloguesubmission4.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

internal class DatabaseFavoredTVShowsHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "favored_tv_shows_db"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE =
            "CREATE TABLE ${DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.TABLE_NAME} " +
                    "(${DatabaseFavoredTVShowsContract.FavoredTVShowsColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "${DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.POSTER_PATH} TEXT NOT NULL, " +
                    "${DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.NAME} TEXT NOT NULL, " +
                    "${DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.GENRE} TEXT NOT NULL, " +
                    "${DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.POPULARITY} REAL NOT NULL, " +
                    "${DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.FIRST_AIR_DATE} TEXT NOT NULL, " +
                    "${DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.LANGUAGE} TEXT NOT NULL, " +
                    "${DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.VOTE_AVERAGE} REAL NOT NULL, " +
                    "${DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.OVERVIEW} TEXT NOT NULL)"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val sql =
            "DROP TABLE IF EXISTS ${DatabaseFavoredMoviesContract.FavoredMoviesColumns.TABLE_NAME}"
        p0?.execSQL(sql)
        onCreate(p0)
    }
}