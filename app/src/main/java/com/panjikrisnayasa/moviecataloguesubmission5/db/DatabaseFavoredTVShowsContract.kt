package com.panjikrisnayasa.moviecataloguesubmission5.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseFavoredTVShowsContract {

    const val AUTHORITY = "com.panjikrisnayasa.moviecataloguesubmission5.tvshows"
    const val SCHEME = "content"

    internal class FavoredTVShowsColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favored_tv_shows"
            const val _ID = "_id"
            const val POSTER_PATH = "poster_path"
            const val NAME = "name"
            const val GENRE = "genre"
            const val POPULARITY = "popularity"
            const val FIRST_AIR_DATE = "first_air_date"
            const val LANGUAGE = "language"
            const val VOTE_AVERAGE = "vote_average"
            const val OVERVIEW = "overview"

            //untuk membuat URI content://com.panjikrisnayasa.moviecataloguesubmission5.tvshows/favored_tv_shows
            val CONTENT_URI_TV_SHOWS: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()

        }
    }
}