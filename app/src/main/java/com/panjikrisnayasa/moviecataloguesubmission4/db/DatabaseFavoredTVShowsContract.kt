package com.panjikrisnayasa.moviecataloguesubmission4.db

import android.provider.BaseColumns

internal class DatabaseFavoredTVShowsContract {
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
        }
    }
}