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
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MoviesViewModel : ViewModel() {
    companion object {
        private const val BASE_URL_MOVIE = "https://api.themoviedb.org/3/discover/movie"
        private const val BASE_URL_SEARCH_MOVIE = "https://api.themoviedb.org/3/search/movie"
        private const val API_KEY_PARAM = "api_key"
        private const val LANGUAGE_PARAM = "language"
        private const val LANGUAGE = "en-US"
        private const val QUERY_PARAM = "query"
    }

    val moviesData = MutableLiveData<ArrayList<Movie>>()
    private val mClient = AsyncHttpClient()

    internal fun setMovies(context: Context) {
        val listItems = ArrayList<Movie>()
        val builtUri =
            Uri.parse(BASE_URL_MOVIE).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MY_API_KEY)
                .appendQueryParameter(LANGUAGE_PARAM, LANGUAGE)
                .build()
        val url = builtUri.toString()

        mClient.get(url, object : AsyncHttpResponseHandler() {
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
                    val resultM = tResult
                    val responseObjectM = JSONObject(resultM)
                    val listM = responseObjectM.getJSONArray("results")

                    for (i in 0 until listM.length()) {
                        val listMovies = listM.getJSONObject(i)
                        val movie =
                            Movie()
                        movie.id = listMovies.getLong("id").toString()
                        movie.posterPath = listMovies.getString("poster_path")
                        movie.title = listMovies.getString("title")
                        movie.popularity = listMovies.getDouble("popularity")
                        movie.voteAverage = listMovies.getDouble("vote_average")
                        movie.releaseDate = listMovies.getString("release_date")
                        movie.forAdult = listMovies.getBoolean("adult")

                        listItems.add(movie)
                    }
                    moviesData.postValue(listItems)

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

    internal fun getMovies(): LiveData<ArrayList<Movie>> {
        return moviesData
    }

    internal fun setSearchMovies(context: Context, query: String) {
        val listItems = ArrayList<Movie>()
        val builtUri =
            Uri.parse(BASE_URL_SEARCH_MOVIE).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MY_API_KEY)
                .appendQueryParameter(LANGUAGE_PARAM, LANGUAGE)
                .appendQueryParameter(QUERY_PARAM, query)
                .build()
        val url = builtUri.toString()

        mClient.get(url, object : AsyncHttpResponseHandler() {
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
                    val resultM = tResult
                    val responseObjectM = JSONObject(resultM)
                    val listM = responseObjectM.getJSONArray("results")

                    for (i in 0 until listM.length()) {
                        val listMovies = listM.getJSONObject(i)
                        val movie =
                            Movie()
                        movie.id = listMovies.getLong("id").toString()
                        movie.posterPath = listMovies.getString("poster_path")
                        movie.title = listMovies.getString("title")
                        movie.popularity = listMovies.getDouble("popularity")
                        movie.voteAverage = listMovies.getDouble("vote_average")
                        movie.releaseDate = listMovies.getString("release_date")
                        movie.forAdult = listMovies.getBoolean("adult")

                        listItems.add(movie)
                    }
                    moviesData.postValue(listItems)

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
}