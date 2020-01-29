package com.panjikrisnayasa.moviecataloguesubmission4.helper

import android.database.Cursor
import com.panjikrisnayasa.moviecataloguesubmission4.db.DatabaseFavoredMoviesContract
import com.panjikrisnayasa.moviecataloguesubmission4.model.Movie

object MappingHelper {

    fun mapFavoredMovieCursorToArrayList(movieCursor: Cursor): ArrayList<Movie> {
        val moviesList = ArrayList<Movie>()
        movieCursor.moveToFirst()
        while (movieCursor.moveToNext()) {
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
        }
        return moviesList
    }
}