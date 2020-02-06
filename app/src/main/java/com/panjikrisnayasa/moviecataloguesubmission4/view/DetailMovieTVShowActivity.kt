package com.panjikrisnayasa.moviecataloguesubmission4.view

import android.content.ContentValues
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.panjikrisnayasa.moviecataloguesubmission4.R
import com.panjikrisnayasa.moviecataloguesubmission4.db.DatabaseFavoredMoviesContract
import com.panjikrisnayasa.moviecataloguesubmission4.db.DatabaseFavoredTVShowsContract
import com.panjikrisnayasa.moviecataloguesubmission4.db.FavoredMoviesHelper
import com.panjikrisnayasa.moviecataloguesubmission4.db.FavoredTVShowsHelper
import com.panjikrisnayasa.moviecataloguesubmission4.helper.MappingHelper
import com.panjikrisnayasa.moviecataloguesubmission4.model.Movie
import com.panjikrisnayasa.moviecataloguesubmission4.model.TVShow
import com.panjikrisnayasa.moviecataloguesubmission4.viewmodel.DetailMovieTVShowViewModel
import kotlinx.android.synthetic.main.activity_detail_movie_tvshow.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class DetailMovieTVShowActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
        const val EXTRA_TVSHOW_ID = "extra_tvshow_id"
        private const val EXTRA_STATE = "extra_state"
    }

    private lateinit var mDetailMovieTVShowViewModel: DetailMovieTVShowViewModel
    private lateinit var mFavoredMoviesHelper: FavoredMoviesHelper
    private lateinit var mFavoredTVShowsHelper: FavoredTVShowsHelper
    private var mMenu: Menu? = null
    private var mIsMovieTVShowFavored = false
    private var mIsSavedInDb = false
    private var mDetailMovie: Movie? = null
    private var mDetailTVShow: TVShow? = null
    private var mSavedMovieList = ArrayList<Movie>()
    private var mSavedTVShowList = ArrayList<TVShow>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie_tvshow)

        if (savedInstanceState != null) {
            val isMovieTVShowFavored = savedInstanceState.getBoolean(EXTRA_STATE)
            mIsMovieTVShowFavored = isMovieTVShowFavored
            val menuItem = mMenu?.findItem(R.id.menu_detail_movie_tvshow_favorite)

            if (mIsMovieTVShowFavored) {
                menuItem?.setIcon(R.drawable.ic_favorite_grey_24dp)
            } else {
                menuItem?.setIcon(R.drawable.ic_favorite_border_grey_24dp)
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        image_detail_movies_tvshows_poster.clipToOutline = true

        mFavoredMoviesHelper = FavoredMoviesHelper.getInstance(applicationContext)
        mFavoredMoviesHelper.open()

        mFavoredTVShowsHelper = FavoredTVShowsHelper.getInstance(applicationContext)
        mFavoredTVShowsHelper.open()

        val detailMovie = intent.getStringExtra(EXTRA_MOVIE_ID)
        val detailTVShow = intent.getStringExtra(EXTRA_TVSHOW_ID)

        mDetailMovieTVShowViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                DetailMovieTVShowViewModel::class.java
            )
        showLoading(true)

        if (detailMovie != null) {
            mDetailMovieTVShowViewModel.setMovie(detailMovie, applicationContext)
            mDetailMovieTVShowViewModel.getMovie()
                .observe(this, androidx.lifecycle.Observer { movie ->
                    if (movie != null) {

                        mDetailMovie = movie

                        checkMovie(movie.title!!, mMenu)

                        showLoading(false)

                        text_detail_movies_tvshows_genre_label.text =
                            getString(R.string.item_recycler_fragment_movies_text_genre)
                        text_detail_movies_tvshows_popularity_label.text =
                            getString(R.string.item_recycler_fragment_movies_text_popularity)
                        text_detail_movies_tvshows_release_date_first_air_date_label.text =
                            getString(R.string.item_recycler_fragment_movies_text_release_date)
                        text_detail_movies_tvshows_rating_language_label.text =
                            getString(R.string.item_recycler_fragment_movies_text_rating)
                        text_detail_movies_tvshows_overview_label.text =
                            getString(R.string.detail_movies_tvshows_text_overview)
                        button_detail_movies_tvshows.text =
                            getString(R.string.detail_movies_tvshows_button_buy_ticket)
                        button_detail_movies_tvshows.setOnClickListener {
                            Toast.makeText(
                                this,
                                String.format(
                                    getString(R.string.detail_movies_tvshows_toast_buy_ticket),
                                    movie.title?.toUpperCase(Locale.getDefault())
                                ),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        val posterPath = "https://image.tmdb.org/t/p/w185/" + movie.posterPath
                        Glide.with(this)
                            .load(posterPath)
                            .into(image_detail_movies_tvshows_poster)
                        text_detail_movies_tvshows_title.text = movie.title
                        text_detail_movies_tvshows_genre.text = movie.genre
                        text_detail_movies_tvshows_popularity.text = movie.popularity.toString()
                        text_detail_movies_tvshows_release_date_first_air_date.text =
                            movie.releaseDate
                        text_detail_movies_tvshows_rating_score.text = movie.voteAverage.toString()
                        val voteAverage = (movie.voteAverage?.div(2))?.toFloat()
                        if (voteAverage != null) {
                            rating_detail_movies_tvshows_movie.rating = voteAverage
                        }
                        text_detail_movies_tvshows_overview.text = movie.overview

                        val forAdult = movie.forAdult
                        if (forAdult!!) {
                            text_detail_movies_tvshows_rating_language.text =
                                resources.getString(R.string.movie_rating_adult)
                        } else {
                            text_detail_movies_tvshows_rating_language.text =
                                resources.getString(R.string.movie_rating_all_ages)
                        }
                    }
                })
        } else if (detailTVShow != null) {
            mDetailMovieTVShowViewModel.setTVShow(detailTVShow, applicationContext)
            mDetailMovieTVShowViewModel.getTVShow()
                .observe(this, androidx.lifecycle.Observer { tvShow ->
                    if (tvShow != null) {

                        mDetailTVShow = tvShow

                        checkTVShow(tvShow.name!!, mMenu)

                        showLoading(false)

                        text_detail_movies_tvshows_genre_label.text =
                            getString(R.string.item_recycler_fragment_tvshows_text_genre)
                        text_detail_movies_tvshows_popularity_label.text =
                            getString(R.string.item_recycler_fragment_tvshows_text_popularity)
                        text_detail_movies_tvshows_release_date_first_air_date_label.text =
                            getString(R.string.item_recycler_fragment_tvshows_text_first_air_date)
                        text_detail_movies_tvshows_rating_language_label.text =
                            getString(R.string.item_recycler_fragment_tvshows_text_language)
                        text_detail_movies_tvshows_overview_label.text =
                            getString(R.string.detail_movies_tvshows_text_overview)
                        button_detail_movies_tvshows.text =
                            getString(R.string.detail_movies_tvshows_button_watch_now)
                        button_detail_movies_tvshows.setOnClickListener {
                            Toast.makeText(
                                this,
                                String.format(
                                    getString(R.string.detail_movies_tvshows_toast_watch_now),
                                    tvShow.name?.toUpperCase(Locale.getDefault())
                                ),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        val posterPath = "https://image.tmdb.org/t/p/w185/" + tvShow.posterPath
                        Glide.with(this)
                            .load(posterPath)
                            .into(image_detail_movies_tvshows_poster)
                        text_detail_movies_tvshows_title.text = tvShow.name
                        text_detail_movies_tvshows_genre.text = tvShow.genre
                        text_detail_movies_tvshows_popularity.text = tvShow.popularity.toString()
                        text_detail_movies_tvshows_release_date_first_air_date.text =
                            tvShow.firstAirDate
                        text_detail_movies_tvshows_rating_language.text = tvShow.language
                        text_detail_movies_tvshows_rating_score.text = tvShow.voteAverage.toString()
                        val voteAverage = (tvShow.voteAverage?.div(2))?.toFloat()
                        if (voteAverage != null) {
                            rating_detail_movies_tvshows_movie.rating = voteAverage
                        }
                        text_detail_movies_tvshows_overview.text = tvShow.overview
                    }
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mFavoredMoviesHelper.close()
        mFavoredTVShowsHelper.close()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(EXTRA_STATE, mIsMovieTVShowFavored)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_movie_tvshow, menu)
        if (menu != null) mMenu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_detail_movie_tvshow_favorite -> {
                if (mIsMovieTVShowFavored) {

                    if (mDetailMovie != null) {
                        mIsMovieTVShowFavored = false

                        if (mIsSavedInDb) {
                            mFavoredMoviesHelper.deleteById(mSavedMovieList[0].id.toString())
                        } else {
                            mFavoredMoviesHelper.deleteById(mDetailMovie?.id.toString())
                        }

                        Toast.makeText(
                            this,
                            getString(R.string.toast_movie_unfavored),
                            Toast.LENGTH_SHORT
                        ).show()

                    } else if (mDetailTVShow != null) {
                        mIsMovieTVShowFavored = false

                        if (mIsSavedInDb) {
                            mFavoredTVShowsHelper.deleteById(mSavedTVShowList[0].id.toString())
                        } else {
                            mFavoredTVShowsHelper.deleteById(mDetailTVShow?.id.toString())
                        }

                        Toast.makeText(
                            this,
                            getString(R.string.toast_tvshow_unfavored),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    item.setIcon(R.drawable.ic_favorite_border_grey_24dp)

                } else {

                    mIsMovieTVShowFavored = true
                    val values = ContentValues()

                    if (mDetailMovie != null) {
                        values.put(
                            DatabaseFavoredMoviesContract.FavoredMoviesColumns.POSTER_PATH,
                            mDetailMovie?.posterPath
                        )
                        values.put(
                            DatabaseFavoredMoviesContract.FavoredMoviesColumns.TITLE,
                            mDetailMovie?.title
                        )
                        values.put(
                            DatabaseFavoredMoviesContract.FavoredMoviesColumns.GENRE,
                            mDetailMovie?.genre
                        )
                        values.put(
                            DatabaseFavoredMoviesContract.FavoredMoviesColumns.POPULARITY,
                            mDetailMovie?.popularity
                        )
                        values.put(
                            DatabaseFavoredMoviesContract.FavoredMoviesColumns.RELEASE_DATE,
                            mDetailMovie?.releaseDate
                        )
                        values.put(
                            DatabaseFavoredMoviesContract.FavoredMoviesColumns.RATING,
                            mDetailMovie?.forAdult.toString()
                        )
                        values.put(
                            DatabaseFavoredMoviesContract.FavoredMoviesColumns.VOTE_AVERAGE,
                            mDetailMovie?.voteAverage
                        )
                        values.put(
                            DatabaseFavoredMoviesContract.FavoredMoviesColumns.OVERVIEW,
                            mDetailMovie?.overview
                        )
                        val result = mFavoredMoviesHelper.insert(values)
                        mDetailMovie?.id = result.toString()

                        Toast.makeText(
                            this,
                            getString(R.string.toast_movie_favored),
                            Toast.LENGTH_SHORT
                        ).show()

                    } else if (mDetailTVShow != null) {
                        values.put(
                            DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.POSTER_PATH,
                            mDetailTVShow?.posterPath
                        )
                        values.put(
                            DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.NAME,
                            mDetailTVShow?.name
                        )
                        values.put(
                            DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.GENRE,
                            mDetailTVShow?.genre
                        )
                        values.put(
                            DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.POPULARITY,
                            mDetailTVShow?.popularity
                        )
                        values.put(
                            DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.FIRST_AIR_DATE,
                            mDetailTVShow?.firstAirDate
                        )
                        values.put(
                            DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.LANGUAGE,
                            mDetailTVShow?.language
                        )
                        values.put(
                            DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.VOTE_AVERAGE,
                            mDetailTVShow?.voteAverage
                        )
                        values.put(
                            DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.OVERVIEW,
                            mDetailTVShow?.overview
                        )
                        val result = mFavoredTVShowsHelper.insert(values)
                        mDetailTVShow?.id = result.toString()

                        Toast.makeText(
                            this,
                            getString(R.string.toast_tvshow_favored),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    item.setIcon(R.drawable.ic_favorite_grey_24dp)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkMovie(title: String, menu: Menu?) {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = mFavoredMoviesHelper.queryByTitle(title)
                MappingHelper.mapFavoredMovieCursorToArrayList(cursor)
            }
            val movies = deferredNotes.await()
            if (movies.size > 0) {
                val menuItem = menu?.findItem(R.id.menu_detail_movie_tvshow_favorite)
                menuItem?.setIcon(R.drawable.ic_favorite_grey_24dp)
                mIsMovieTVShowFavored = true
                mIsSavedInDb = true
                mSavedMovieList = movies
            }
        }
    }

    private fun checkTVShow(name: String, menu: Menu?) {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = mFavoredTVShowsHelper.queryByName(name)
                MappingHelper.mapFavoredTVShowCursorToArrayList(cursor)
            }
            val tvShow = deferredNotes.await()
            if (tvShow.size > 0) {
                val menuItem = menu?.findItem(R.id.menu_detail_movie_tvshow_favorite)
                menuItem?.setIcon(R.drawable.ic_favorite_grey_24dp)
                mIsMovieTVShowFavored = true
                mIsSavedInDb = true
                mSavedTVShowList = tvShow
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            image_detail_movies_tvshows_poster.visibility = View.GONE
            rating_detail_movies_tvshows_movie.visibility = View.GONE
            view_detail_movies_tvshows_line.visibility = View.GONE
            button_detail_movies_tvshows.visibility = View.GONE
            progress_detail_movie_tvshow.visibility = View.VISIBLE
        } else {
            progress_detail_movie_tvshow.visibility = View.GONE
            image_detail_movies_tvshows_poster.visibility = View.VISIBLE
            rating_detail_movies_tvshows_movie.visibility = View.VISIBLE
            view_detail_movies_tvshows_line.visibility = View.VISIBLE
            button_detail_movies_tvshows.visibility = View.VISIBLE
        }
    }
}
