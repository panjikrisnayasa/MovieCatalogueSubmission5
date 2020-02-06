package com.panjikrisnayasa.moviecataloguesubmission4.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panjikrisnayasa.moviecataloguesubmission4.R
import com.panjikrisnayasa.moviecataloguesubmission4.model.Movie
import com.panjikrisnayasa.moviecataloguesubmission4.view.DetailFavoredMovieTVShowActivity
import com.panjikrisnayasa.moviecataloguesubmission4.view.FavoriteMoviesFragment
import kotlinx.android.synthetic.main.item_recycler_fragment_movies.view.*

class FavoredMoviesAdapter(private val fragment: FavoriteMoviesFragment) :
    RecyclerView.Adapter<FavoredMoviesAdapter.FavoredMoviesHolder>() {

    companion object {
        private const val BASE_URL = "https://image.tmdb.org/t/p/w185/"
    }

    var listMovies = ArrayList<Movie>()
        set(listMovies) {
            if (listMovies.size > 0) {
                this.listMovies.clear()
            }
            this.listMovies.addAll(listMovies)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoredMoviesHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_fragment_movies, parent, false)
        return FavoredMoviesHolder(view)
    }

    override fun getItemCount(): Int {
        return this.listMovies.size
    }

    override fun onBindViewHolder(holder: FavoredMoviesHolder, position: Int) {
        holder.bind(listMovies[position])
    }

    fun removeItem(position: Int) {
        this.listMovies.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listMovies.size)
    }

    private fun moveToDetail(position: Int, movie: Movie) {
        val detailIntent =
            Intent(fragment.context, DetailFavoredMovieTVShowActivity::class.java)
        detailIntent.putExtra(DetailFavoredMovieTVShowActivity.EXTRA_MOVIE, movie)
        detailIntent.putExtra(DetailFavoredMovieTVShowActivity.EXTRA_POSITION, position)
        fragment.startActivityForResult(
            detailIntent,
            DetailFavoredMovieTVShowActivity.REQUEST_UPDATE
        )
    }

    inner class FavoredMoviesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                image_item_recycler_fragment_movies_poster.clipToOutline = true
                val posterPath = BASE_URL + movie.posterPath
                Glide.with(context).load(posterPath)
                    .into(image_item_recycler_fragment_movies_poster)
                val tVoteAverage = movie.voteAverage
                var voteAverage = 0f
                if (tVoteAverage != null) {
                    voteAverage = (tVoteAverage / 2).toFloat()
                }
                rating_item_recycler_fragment_movies.rating = voteAverage
                text_item_recycler_fragment_movies_vote_average.text = movie.voteAverage.toString()
                text_item_recycler_fragment_movies_title.text = movie.title
                text_item_recycler_fragment_movies_popularity.text = movie.popularity.toString()
                text_item_recycler_fragment_movies_release_date.text = movie.releaseDate
                val forAdult = movie.forAdult
                if (forAdult!!) {
                    text_item_recycler_fragment_movies_rating.text =
                        resources.getString(R.string.movie_rating_adult)
                } else {
                    text_item_recycler_fragment_movies_rating.text =
                        resources.getString(R.string.movie_rating_all_ages)
                }

                button_item_recycler_fragment_movies_details.setOnClickListener {
                    moveToDetail(adapterPosition, movie)
                }
                itemView.setOnClickListener {
                    moveToDetail(adapterPosition, movie)
                }
            }
        }
    }
}