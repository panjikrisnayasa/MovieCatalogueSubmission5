package com.panjikrisnayasa.moviecataloguesubmission2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MoviesAdapter(private var listMovies: ArrayList<MoviesModel>) :
    RecyclerView.Adapter<MoviesAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_fragment_movies, parent, false)
        return MainHolder(view)
    }

    override fun getItemCount(): Int {
        return listMovies.size
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val movie = listMovies[position]

        holder.mImagePoster.clipToOutline = true

        Glide.with(holder.itemView.context)
            .load(movie.poster)
            .apply(RequestOptions())
            .into(holder.mImagePoster)

        holder.mTextTitle.text = movie.title
        holder.mRatingMovie.rating = movie.ratingScore / 2
        holder.mTextRatingScore.text = movie.ratingScore.toString()
        holder.mTextGenre.text = movie.genre
        holder.mTextDuration.text = movie.duration
        holder.mTextRating.text = movie.rating

        holder.mButtonDetails.setOnClickListener {
            moveToMoviesDetail(movie, it)
        }
        holder.itemView.setOnClickListener {
            moveToMoviesDetail(movie, it)
        }
    }

    private fun moveToMoviesDetail(
        movies: MoviesModel,
        it: View
    ) {
        val detailMoviesIntent = Intent(it.context, DetailMoviesTVShowsActivity::class.java)
        detailMoviesIntent.putExtra(DetailMoviesTVShowsActivity.EXTRA_MOVIES, movies)
        it.context.startActivity(detailMoviesIntent)
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImagePoster: ImageView =
            itemView.findViewById(R.id.image_item_recycler_fragment_movies_poster)
        var mTextTitle: TextView =
            itemView.findViewById(R.id.text_item_recycler_fragment_movies_title)
        var mRatingMovie: RatingBar =
            itemView.findViewById(R.id.rating_item_recycler_fragment_movies)
        var mTextRatingScore: TextView =
            itemView.findViewById(R.id.text_item_recycler_fragment_movies_rating_score)
        var mTextGenre: TextView =
            itemView.findViewById(R.id.text_item_recycler_fragment_movies_genre)
        var mTextDuration: TextView =
            itemView.findViewById(R.id.text_item_recycler_fragment_movies_duration)
        var mTextRating: TextView =
            itemView.findViewById(R.id.text_item_recycler_fragment_movies_rating)
        var mButtonDetails: TextView =
            itemView.findViewById(R.id.button_item_recycler_fragment_movies_details)
    }
}