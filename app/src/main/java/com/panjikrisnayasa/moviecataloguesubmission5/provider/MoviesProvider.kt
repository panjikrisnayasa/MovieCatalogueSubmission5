package com.panjikrisnayasa.moviecataloguesubmission5.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.panjikrisnayasa.moviecataloguesubmission5.db.DatabaseFavoredMoviesContract.AUTHORITY
import com.panjikrisnayasa.moviecataloguesubmission5.db.DatabaseFavoredMoviesContract.FavoredMoviesColumns.Companion.CONTENT_URI_MOVIES
import com.panjikrisnayasa.moviecataloguesubmission5.db.DatabaseFavoredMoviesContract.FavoredMoviesColumns.Companion.TABLE_NAME
import com.panjikrisnayasa.moviecataloguesubmission5.db.FavoredMoviesHelper

class MoviesProvider : ContentProvider() {

    companion object {
        private const val MOVIE = 1
        private const val MOVIE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var favoredMoviesHelper: FavoredMoviesHelper

        init {

            // content://com.panjikrisnayasa.moviecataloguesubmission5.movies/favored_movies
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, MOVIE)

            // content://com.panjikrisnayasa.moviecataloguesubmission5.movies/favored_movies/id
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", MOVIE_ID)

        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (MOVIE_ID) {
            sUriMatcher.match(uri) -> favoredMoviesHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI_MOVIES, null)

        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (MOVIE) {
            sUriMatcher.match(uri) -> favoredMoviesHelper.insert(values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI_MOVIES, null)

        return Uri.parse("$CONTENT_URI_MOVIES/$added")
    }

    override fun onCreate(): Boolean {
        favoredMoviesHelper = FavoredMoviesHelper.getInstance(context as Context)
        favoredMoviesHelper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            MOVIE -> cursor = favoredMoviesHelper.queryAll()
            MOVIE_ID -> cursor = favoredMoviesHelper.queryById(uri.lastPathSegment.toString())
            else -> cursor = null
        }
        return cursor
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

}
