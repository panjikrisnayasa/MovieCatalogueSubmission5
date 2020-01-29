package com.panjikrisnayasa.moviecataloguesubmission4.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.panjikrisnayasa.moviecataloguesubmission4.R
import com.panjikrisnayasa.moviecataloguesubmission4.model.Movie
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MoviesViewModel : ViewModel() {
    companion object {
        private const val API_KEY = "cf6b5328dcc68b107a713f503eb6e1d1"
    }

    val moviesData = MutableLiveData<ArrayList<Movie>>()

    internal fun setMovies(context: Context) {
        val client = AsyncHttpClient()
        val listItems = ArrayList<Movie>()
        val moviesUrl =
            "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&language=en-US"

        client.get(moviesUrl, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    val resultM = responseBody?.let { String(it) }
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
}