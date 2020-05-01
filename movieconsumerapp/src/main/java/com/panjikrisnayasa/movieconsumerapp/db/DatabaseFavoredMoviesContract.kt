package com.panjikrisnayasa.movieconsumerapp.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseFavoredMoviesContract {

    const val AUTHORITY = "com.panjikrisnayasa.moviecataloguesubmission5.movies"
    const val SCHEME = "content"

    internal class FavoredMoviesColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favored_movies"
            const val _ID = "_id"
            const val POSTER_PATH = "poster_path"
            const val TITLE = "title"
            const val GENRE = "genre"
            const val POPULARITY = "popularity"
            const val RELEASE_DATE = "release_date"
            const val RATING = "rating"
            const val VOTE_AVERAGE = "vote_average"
            const val OVERVIEW = "overview"

            //untuk membuat URI content://com.panjikrisnayasa.moviecataloguesubmission5.movies/favored_movies
            val CONTENT_URI_MOVIES: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()

        }
    }
}