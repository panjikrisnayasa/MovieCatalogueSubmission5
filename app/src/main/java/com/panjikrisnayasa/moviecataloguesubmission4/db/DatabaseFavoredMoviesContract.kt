package com.panjikrisnayasa.moviecataloguesubmission4.db

import android.provider.BaseColumns

internal class DatabaseFavoredMoviesContract {
    internal class FavoredMoviesColumns: BaseColumns {
        companion object {
            const val TABLE_NAME = "favored_movies"
            const val _ID = "_id"
            const val MOVIE_ID = "movie_id"
            const val POSTER_PATH = "poster_path"
            const val TITLE = "title"
            const val GENRE = "genre"
            const val POPULARITY = "popularity"
            const val RELEASE_DATE = "release_date"
            const val RATING = "rating"
            const val VOTE_AVERAGE = "vote_average"
            const val OVERVIEW = "overview"
        }
    }
}