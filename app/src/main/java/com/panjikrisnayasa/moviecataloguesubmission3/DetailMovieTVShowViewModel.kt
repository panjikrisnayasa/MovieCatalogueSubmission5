package com.panjikrisnayasa.moviecataloguesubmission3

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailMovieTVShowViewModel : ViewModel() {
    companion object {
        private const val API_KEY = "cf6b5328dcc68b107a713f503eb6e1d1"
    }

    val movieData = MutableLiveData<Movie>()
    val tvShowData = MutableLiveData<TVShow>()

    internal fun setMovie(movieID: String, context: Context) {
        val client = AsyncHttpClient()
        val url = "https://api.themoviedb.org/3/movie/$movieID?api_key=$API_KEY&language=en-US"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    val result = String(responseBody!!)
                    val responseObject = JSONObject(result)

                    val movie = Movie()
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
                Toast.makeText(context, context.getString(R.string.failed_to_retrieve_data), Toast.LENGTH_SHORT).show()
                error?.printStackTrace()
            }

        })
    }

    internal fun getMovie(): LiveData<Movie> {
        return movieData
    }

    internal fun setTVShow(TVShowID: String, context: Context) {
        val client = AsyncHttpClient()
        val url = "https://api.themoviedb.org/3/tv/$TVShowID?api_key=$API_KEY&language=en-US"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    val result = String(responseBody!!)
                    val responseObject = JSONObject(result)

                    val tvShow = TVShow()
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
                Toast.makeText(context, context.getString(R.string.failed_to_retrieve_data), Toast.LENGTH_SHORT).show()
                error?.printStackTrace()
            }

        })
    }

    internal fun getTVShow(): LiveData<TVShow> {
        return tvShowData
    }
}