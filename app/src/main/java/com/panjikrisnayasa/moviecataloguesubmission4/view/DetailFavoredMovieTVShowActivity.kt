package com.panjikrisnayasa.moviecataloguesubmission4.view

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.panjikrisnayasa.moviecataloguesubmission4.R
import com.panjikrisnayasa.moviecataloguesubmission4.db.DatabaseFavoredMoviesContract
import com.panjikrisnayasa.moviecataloguesubmission4.db.DatabaseFavoredTVShowsContract
import com.panjikrisnayasa.moviecataloguesubmission4.db.FavoredMoviesHelper
import com.panjikrisnayasa.moviecataloguesubmission4.db.FavoredTVShowsHelper
import com.panjikrisnayasa.moviecataloguesubmission4.model.Movie
import com.panjikrisnayasa.moviecataloguesubmission4.model.TVShow
import kotlinx.android.synthetic.main.activity_detail_movie_tvshow.*
import java.util.*

class DetailFavoredMovieTVShowActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TVSHOW = "extra_tvshow"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_UPDATE = 100
        const val RESULT_ADD = 201
        const val RESULT_DELETE = 301
        private const val EXTRA_STATE = "extra_state"
    }

    private lateinit var mFavoredMoviesHelper: FavoredMoviesHelper
    private lateinit var mFavoredTVShowsHelper: FavoredTVShowsHelper
    private var mIsMovieTVShowFavored = true
    private var mPosition: Int = 0
    private var mDetailMovie: Movie? = null
    private var mDetailTVShow: TVShow? = null
    private var mMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie_tvshow)

        if (savedInstanceState != null) {
            val isMovieTVShowFavored = savedInstanceState.getBoolean(EXTRA_STATE)
            mIsMovieTVShowFavored = isMovieTVShowFavored
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        image_detail_movies_tvshows_poster.clipToOutline = true

        mFavoredMoviesHelper = FavoredMoviesHelper.getInstance(applicationContext)
        mFavoredMoviesHelper.open()

        mFavoredTVShowsHelper = FavoredTVShowsHelper.getInstance(applicationContext)
        mFavoredTVShowsHelper.open()

        try {
            val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
            val tvShow = intent.getParcelableExtra<TVShow>(EXTRA_TVSHOW)
            mPosition = intent.getIntExtra(EXTRA_POSITION, 0)

            showLoading(true)

            if (movie != null) {
                mDetailMovie = movie

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
            } else if (tvShow != null) {
                mDetailTVShow = tvShow

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
        } catch (e: Exception) {
            e.printStackTrace()
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

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val favoriteMenu = menu?.findItem(R.id.menu_detail_movie_tvshow_favorite)
        if (mIsMovieTVShowFavored) {
            favoriteMenu?.setIcon(R.drawable.ic_favorite_grey_24dp)
        } else {
            favoriteMenu?.setIcon(R.drawable.ic_favorite_border_grey_24dp)

            val intent = Intent()
            intent.putExtra(EXTRA_POSITION, mPosition)
            setResult(RESULT_DELETE, intent)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            R.id.menu_detail_movie_tvshow_favorite -> {

                val intent = Intent()
                intent.putExtra(EXTRA_POSITION, mPosition)

                if (mIsMovieTVShowFavored) {
                    if (mDetailMovie != null) {
                        intent.putExtra(EXTRA_MOVIE, mDetailMovie)
                        mIsMovieTVShowFavored = false

                        mFavoredMoviesHelper.deleteById(mDetailMovie?.id.toString())

                        Toast.makeText(
                            this,
                            getString(R.string.toast_movie_unfavored),
                            Toast.LENGTH_SHORT
                        ).show()

                    } else if (mDetailTVShow != null) {
                        intent.putExtra(EXTRA_TVSHOW, mDetailTVShow)
                        mIsMovieTVShowFavored = false

                        mFavoredTVShowsHelper.deleteById(mDetailTVShow?.id.toString())

                        Toast.makeText(
                            this,
                            getString(R.string.toast_tvshow_unfavored),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    item.setIcon(R.drawable.ic_favorite_border_grey_24dp)
                    setResult(RESULT_DELETE, intent)

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
                    setResult(RESULT_ADD, intent)

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