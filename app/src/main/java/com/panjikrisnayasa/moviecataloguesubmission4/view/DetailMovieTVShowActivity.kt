package com.panjikrisnayasa.moviecataloguesubmission4.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.panjikrisnayasa.moviecataloguesubmission4.R
import com.panjikrisnayasa.moviecataloguesubmission4.viewmodel.DetailMovieTVShowViewModel
import kotlinx.android.synthetic.main.activity_detail_movie_tvshow.*
import java.util.*

class DetailMovieTVShowActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIES = "extra_movies"
        const val EXTRA_TVSHOWS = "extra_tvshows"
    }

    private lateinit var mDetailMovieTVShowViewModel: DetailMovieTVShowViewModel
    private var mIsMovieTVShowFavored = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie_tvshow)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        image_detail_movies_tvshows_poster.clipToOutline = true

        val detailMovie = intent.getStringExtra(EXTRA_MOVIES)
        val detailTVShow = intent.getStringExtra(EXTRA_TVSHOWS)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_movie_tvshow, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            R.id.menu_detail_movie_tvshow_favorite -> {
                if (mIsMovieTVShowFavored) {
                    mIsMovieTVShowFavored = false
                    Toast.makeText(
                        this,
                        "Movie or TV Show is favored $mIsMovieTVShowFavored",
                        Toast.LENGTH_SHORT
                    ).show()
                    item.setIcon(R.drawable.ic_favorite_border_grey_24dp)
                } else {
                    mIsMovieTVShowFavored = true
                    Toast.makeText(
                        this,
                        "Movie or TV Show is favored $mIsMovieTVShowFavored",
                        Toast.LENGTH_SHORT
                    ).show()
                    item.setIcon(R.drawable.ic_favorite_grey_24dp)
                }
            }
        }
        return super.onOptionsItemSelected(item)
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
