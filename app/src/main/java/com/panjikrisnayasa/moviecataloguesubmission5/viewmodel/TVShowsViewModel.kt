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
import com.panjikrisnayasa.moviecataloguesubmission5.model.TVShow
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class TVShowsViewModel : ViewModel() {
    companion object {
        private const val BASE_URL_TV_SHOW = "https://api.themoviedb.org/3/discover/tv"
        private const val BASE_URL_SEARCH_TV_SHOW = "https://api.themoviedb.org/3/search/tv"
        private const val API_KEY_PARAM = "api_key"
        private const val LANGUAGE_PARAM = "language"
        private const val LANGUAGE = "en-US"
        private const val QUERY_PARAM = "query"
    }

    val tvShowsData = MutableLiveData<ArrayList<TVShow>>()
    private val mClient = AsyncHttpClient()

    internal fun setTVShows(context: Context) {
        val listItems = ArrayList<TVShow>()
        val builtUri =
            Uri.parse(BASE_URL_TV_SHOW).buildUpon()
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
                    val resultT = tResult
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

    internal fun setSearchTVShows(context: Context, query: String) {
        val listItems = ArrayList<TVShow>()
        val builtUri =
            Uri.parse(BASE_URL_SEARCH_TV_SHOW).buildUpon()
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
                    val resultT = tResult
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
}