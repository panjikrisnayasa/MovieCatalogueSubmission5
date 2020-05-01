package com.panjikrisnayasa.moviecataloguesubmission5.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.panjikrisnayasa.moviecataloguesubmission5.db.DatabaseFavoredTVShowsContract.AUTHORITY
import com.panjikrisnayasa.moviecataloguesubmission5.db.DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.Companion.CONTENT_URI_TV_SHOWS
import com.panjikrisnayasa.moviecataloguesubmission5.db.DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.Companion.TABLE_NAME
import com.panjikrisnayasa.moviecataloguesubmission5.db.FavoredTVShowsHelper

class TVShowsProvider : ContentProvider() {

    companion object {
        private const val TVSHOW = 1
        private const val TVSHOW_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var favoredTVShowsHelper: FavoredTVShowsHelper

        init {

            // content://com.panjikrisnayasa.moviecataloguesubmission5.tvshows/favored_tvshows
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, TVSHOW)

            // content://com.panjikrisnayasa.moviecataloguesubmission5.tvshows/favored_tvshows/id
            sUriMatcher.addURI(AUTHORITY, "${TABLE_NAME}/#", TVSHOW_ID)

        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (TVSHOW_ID) {
            sUriMatcher.match(uri) -> favoredTVShowsHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI_TV_SHOWS, null)

        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (TVSHOW) {
            sUriMatcher.match(uri) -> favoredTVShowsHelper.insert(values)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI_TV_SHOWS, null)

        return Uri.parse("${CONTENT_URI_TV_SHOWS}/$added")
    }

    override fun onCreate(): Boolean {
        favoredTVShowsHelper = FavoredTVShowsHelper.getInstance(context as Context)
        favoredTVShowsHelper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            TVSHOW -> favoredTVShowsHelper.queryAll()
            TVSHOW_ID -> favoredTVShowsHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}
