package com.panjikrisnayasa.moviecataloguesubmission5.viewmodel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.panjikrisnayasa.moviecataloguesubmission5.BuildConfig
import com.panjikrisnayasa.moviecataloguesubmission5.R
import com.panjikrisnayasa.moviecataloguesubmission5.model.Movie
import com.panjikrisnayasa.moviecataloguesubmission5.model.TVShow
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailMovieTVShowViewModel : ViewModel() {
    companion object {
        private const val BASE_URL_MOVIE = "https://api.themoviedb.org/3/movie/"
        private const val BASE_URL_TV_SHOW = "https://api.themoviedb.org/3/tv/"
        private const val API_KEY_PARAM = "api_key"
        private const val LANGUAGE_PARAM = "language"
        private const val LANGUAGE = "en-US"
    }

    val movieData = MutableLiveData<Movie>()
    val tvShowData = MutableLiveData<TVShow>()

    internal fun setMovie(movieID: String, context: Context) {
        val client = AsyncHttpClient()
        val builtUri =
            Uri.parse(BASE_URL_MOVIE).buildUpon()
                .appendPath(movieID)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MY_API_KEY)
                .appendQueryParameter(LANGUAGE_PARAM, LANGUAGE)
                .build()
        val url = builtUri.toString()

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    var tResult = ""
                    if (responseBody != null) {
                        tResult = String(responseBody)
                    }
                    val result = tResult
                    val responseObject = JSONObject(result)

                    val movie =
                        Movie()
                    movie.id = responseObject.getLong("id").toString()
                    movie.posterPath = responseObject.getString("poster_path")
                    movie.title = responseObject.getString("title")
                    movie.genre =
                        responseObject.getJSONArray("genres").getJSONObject(0).getString("name")
                    movie.popularity = responseObject.getDouble("popularity")
                    movie.releaseDate = responseObject.getString("release_date")
                    movie.forAdult = responseObject.getBoolean("adult")
                    movie.voteAverage = responseObject.getDouble("vote_average")
                    movie.overview = responseObject.getString("overview")

                    movieData.postValue(movie)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Toast.makeText(
                    context,
                    context.getString(R.string.failed_to_retrieve_data),
                    Toast.LENGTH_SHORT
                ).show()
                error?.printStackTrace()
            }

        })
    }

    internal fun getMovie(): LiveData<Movie> {
        return movieData
    }

    internal fun setTVShow(TVShowID: String, context: Context) {
        val client = AsyncHttpClient()
        val builtUri =
            Uri.parse(BASE_URL_TV_SHOW).buildUpon()
                .appendPath(TVShowID)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MY_API_KEY)
                .appendQueryParameter(LANGUAGE_PARAM, LANGUAGE)
                .build()
        val url = builtUri.toString()

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    var tResult = ""
                    if (responseBody != null) {
                        tResult = String(responseBody)
                    }
                    val result = tResult
                    val responseObject = JSONObject(result)

                    val tvShow = TVShow()
                    tvShow.id = responseObject.getLong("id").toString()
                    tvShow.posterPath = responseObject.getString("poster_path")
                    tvShow.name = responseObject.getString("name")
                    tvShow.voteAverage = responseObject.getDouble("vote_average")
                    tvShow.genre =
                        responseObject.getJSONArray("genres").getJSONObject(0).getString("name")
                    tvShow.popularity = responseObject.getDouble("popularity")
                    tvShow.firstAirDate = responseObject.getString("first_air_date")
                    tvShow.language = responseObject.getString("original_language")
                    tvShow.overview = responseObject.getString("overview")

                    tvShowData.postValue(tvShow)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Toast.makeText(
                    context,
                    context.getString(R.string.failed_to_retrieve_data),
                    Toast.LENGTH_SHORT
                ).show()
                error?.printStackTrace()
            }

        })
    }

    internal fun getTVShow(): LiveData<TVShow> {
        return tvShowData
    }

}