package com.panjikrisnayasa.moviecataloguesubmission4.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.panjikrisnayasa.moviecataloguesubmission4.R
import com.panjikrisnayasa.moviecataloguesubmission4.model.TVShow
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class TVShowsViewModel : ViewModel() {
    companion object {
        private const val API_KEY = "cf6b5328dcc68b107a713f503eb6e1d1"
    }

    val tvShowsData = MutableLiveData<ArrayList<TVShow>>()

    internal fun setTVShows(context: Context) {
        val client = AsyncHttpClient()
        val listItems = ArrayList<TVShow>()
        val tvShowsUrl = "https://api.themoviedb.org/3/discover/tv?api_key=$API_KEY&language=en-US"

        client.get(tvShowsUrl, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    val resultT = responseBody?.let { String(it) }
                    val responseObjectT = JSONObject(resultT)
                    val listT = responseObjectT.getJSONArray("results")

                    for (i in 0 until listT.length()) {
                        val tvShowsList = listT.getJSONObject(i)
                        val tvShow =
                            TVShow()
                        tvShow.id = tvShowsList.getLong("id").toString()
                        tvShow.posterPath = tvShowsList.getString("poster_path")
                        tvShow.name = tvShowsList.getString("name")
                        tvShow.voteAverage = tvShowsList.getDouble("vote_average")
                        tvShow.popularity = tvShowsList.getDouble("popularity")
                        tvShow.firstAirDate = tvShowsList.getString("first_air_date")
                        tvShow.language = tvShowsList.getString("original_language")

                        listItems.add(tvShow)
                    }
                    tvShowsData.postValue(listItems)

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

    internal fun getTVShows(): LiveData<ArrayList<TVShow>> {
        return tvShowsData
    }
}