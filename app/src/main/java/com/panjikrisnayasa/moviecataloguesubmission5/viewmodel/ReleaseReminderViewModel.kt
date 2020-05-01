package com.panjikrisnayasa.moviecataloguesubmission5.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.panjikrisnayasa.moviecataloguesubmission5.BuildConfig
import com.panjikrisnayasa.moviecataloguesubmission5.R
import com.panjikrisnayasa.moviecataloguesubmission5.model.Movie
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReleaseReminderViewModel {

    companion object {
        private const val BASE_URL_MOVIE = "https://api.themoviedb.org/3/discover/movie"
        private const val API_KEY_PARAM = "api_key"
        private const val PRIMARY_RELEASE_DATE_GTE = "primary_release_date.gte"
        private const val PRIMARY_RELEASE_DATE_LTE = "primary_release_date.lte"
    }

    val moviesData = ArrayList<Movie>()
    private val mClient = AsyncHttpClient()

    internal fun setMovies(context: Context) {
        val currentDate = Calendar.getInstance().time
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val formattedDate = df.format(currentDate)

        val listItems = ArrayList<Movie>()
        val builtUri =
            Uri.parse(BASE_URL_MOVIE).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.MY_API_KEY)
                .appendQueryParameter(PRIMARY_RELEASE_DATE_GTE, formattedDate)
                .appendQueryParameter(PRIMARY_RELEASE_DATE_LTE, formattedDate)
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
                        if (listM.length() > 2) return
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

                        Log.d("morgan", "${movie.title}")

                        listItems.add(movie)
                    }
                    moviesData.addAll(listItems)
                    Log.d("morgan", "${moviesData.size}")

                } catch (e: Exception) {
                    Log.d("morgan", "catch")
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

                Log.d("morgan", "onfailure")
            }
        })
    }

    internal fun getMovies(): ArrayList<Movie> {
        Log.d("morgan", "${moviesData[0].title}")
        return moviesData
    }
}