package com.panjikrisnayasa.movieconsumerapp.helper

import android.database.Cursor
import com.panjikrisnayasa.movieconsumerapp.model.Movie
import com.panjikrisnayasa.movieconsumerapp.model.TVShow
import com.panjikrisnayasa.movieconsumerapp.db.DatabaseFavoredMoviesContract
import com.panjikrisnayasa.movieconsumerapp.db.DatabaseFavoredTVShowsContract

object MappingHelper {

    fun mapFavoredMovieCursorToArrayList(movieCursor: Cursor?): ArrayList<Movie> {
        val moviesList = ArrayList<Movie>()
        movieCursor?.moveToFirst()
        if (movieCursor != null) {
            if (movieCursor.isFirst) {
                do {
                    val id = movieCursor.getString(
                        movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns._ID)
                    )
                    val posterPath = movieCursor.getString(
                        movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns.POSTER_PATH)
                    )
                    val title = movieCursor.getString(
                        movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns.TITLE)
                    )
                    val genre = movieCursor.getString(
                        movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns.GENRE)
                    )
                    val popularity = movieCursor.getDouble(
                        movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns.POPULARITY)
                    )
                    val releaseDate = movieCursor.getString(
                        movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns.RELEASE_DATE)
                    )
                    val rating = movieCursor.getString(
                        movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns.RATING)
                    )
                    val voteAverage = movieCursor.getDouble(
                        movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns.VOTE_AVERAGE)
                    )
                    val overview = movieCursor.getString(
                        movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns.OVERVIEW)
                    )
                    moviesList.add(
                        Movie(
                            id,
                            posterPath,
                            title,
                            voteAverage,
                            popularity,
                            genre,
                            releaseDate,
                            rating!!.toBoolean(),
                            overview
                        )
                    )
                } while (movieCursor.moveToNext())
            }
        }
        return moviesList
    }

    fun mapFavoredMovieCursorToObject(movieCursor: Cursor?): Movie {
        var movie = Movie()
        movieCursor?.apply {
            moveToFirst()
            val id = movieCursor.getString(
                movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns._ID)
            )
            val posterPath = movieCursor.getString(
                movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns.POSTER_PATH)
            )
            val title = movieCursor.getString(
                movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns.TITLE)
            )
            val genre = movieCursor.getString(
                movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns.GENRE)
            )
            val popularity = movieCursor.getDouble(
                movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns.POPULARITY)
            )
            val releaseDate = movieCursor.getString(
                movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns.RELEASE_DATE)
            )
            val rating = movieCursor.getString(
                movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns.RATING)
            )
            val voteAverage = movieCursor.getDouble(
                movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns.VOTE_AVERAGE)
            )
            val overview = movieCursor.getString(
                movieCursor.getColumnIndexOrThrow(DatabaseFavoredMoviesContract.FavoredMoviesColumns.OVERVIEW)
            )
            movie = Movie(
                id,
                posterPath,
                title,
                voteAverage,
                popularity,
                genre,
                releaseDate,
                rating!!.toBoolean(),
                overview
            )
        }
        return movie
    }

    fun mapFavoredTVShowCursorToArrayList(tvShowCursor: Cursor?): ArrayList<TVShow> {
        val tvShowsList = ArrayList<TVShow>()
        tvShowCursor?.moveToFirst()
        if (tvShowCursor != null) {
            if (tvShowCursor.isFirst) {
                do {
                    val id = tvShowCursor.getString(
                        tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns._ID)
                    )
                    val posterPath = tvShowCursor.getString(
                        tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.POSTER_PATH)
                    )
                    val name = tvShowCursor.getString(
                        tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.NAME)
                    )
                    val genre = tvShowCursor.getString(
                        tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.GENRE)
                    )
                    val popularity = tvShowCursor.getDouble(
                        tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.POPULARITY)
                    )
                    val firstAirDate = tvShowCursor.getString(
                        tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.FIRST_AIR_DATE)
                    )
                    val language = tvShowCursor.getString(
                        tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.LANGUAGE)
                    )
                    val voteAverage = tvShowCursor.getDouble(
                        tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.VOTE_AVERAGE)
                    )
                    val overview = tvShowCursor.getString(
                        tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.OVERVIEW)
                    )
                    tvShowsList.add(
                        TVShow(
                            id,
                            posterPath,
                            name,
                            voteAverage,
                            genre,
                            popularity,
                            firstAirDate,
                            language,
                            overview
                        )
                    )
                } while (tvShowCursor.moveToNext())
            }
        }
        return tvShowsList
    }

    fun mapFavoredTVShowCursorToObject(tvShowCursor: Cursor?): TVShow {
        var tvShow = TVShow()
        tvShowCursor?.apply {
            moveToFirst()
            val id = tvShowCursor.getString(
                tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns._ID)
            )
            val posterPath = tvShowCursor.getString(
                tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.POSTER_PATH)
            )
            val name = tvShowCursor.getString(
                tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.NAME)
            )
            val genre = tvShowCursor.getString(
                tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.GENRE)
            )
            val popularity = tvShowCursor.getDouble(
                tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.POPULARITY)
            )
            val firstAirDate = tvShowCursor.getString(
                tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.FIRST_AIR_DATE)
            )
            val language = tvShowCursor.getString(
                tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.LANGUAGE)
            )
            val voteAverage = tvShowCursor.getDouble(
                tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.VOTE_AVERAGE)
            )
            val overview = tvShowCursor.getString(
                tvShowCursor.getColumnIndexOrThrow(DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.OVERVIEW)
            )
            tvShow = TVShow(
                id,
                posterPath,
                name,
                voteAverage,
                genre,
                popularity,
                firstAirDate,
                language,
                overview
            )
        }
        return tvShow
    }

}