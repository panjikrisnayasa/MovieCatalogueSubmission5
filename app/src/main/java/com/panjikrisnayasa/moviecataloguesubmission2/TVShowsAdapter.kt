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

class TVShowsAdapter(private var listTVShows: ArrayList<TVShowsModel>) :
    RecyclerView.Adapter<TVShowsAdapter.TVShowsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_fragment_tvshows, parent, false)
        return TVShowsHolder(view)
    }

    override fun getItemCount(): Int {
        return listTVShows.size
    }

    override fun onBindViewHolder(holder: TVShowsHolder, position: Int) {
        val tvShows = listTVShows[position]

        holder.mImagePoster.clipToOutline = true

        Glide.with(holder.itemView.context)
            .load(tvShows.poster)
            .apply(RequestOptions())
            .into(holder.mImagePoster)

        holder.mTextTitle.text = tvShows.title
        holder.mRatingMovie.rating = tvShows.ratingScore / 2
        holder.mTextRatingScore.text = tvShows.ratingScore.toString()
        holder.mTextGenre.text = tvShows.genre
        holder.mTextRuntime.text = tvShows.runtime
        holder.mTextNetwork.text = tvShows.network

        holder.mButtonDetails.setOnClickListener {
            moveToTVShowsDetail(tvShows, it)
        }
        holder.itemView.setOnClickListener {
            moveToTVShowsDetail(tvShows, it)
        }
    }

    private fun moveToTVShowsDetail(
        tvShows: TVShowsModel,
        it: View
    ) {
        val detailTVShowsIntent = Intent(it.context, DetailMoviesTVShowsActivity::class.java)
        detailTVShowsIntent.putExtra(DetailMoviesTVShowsActivity.EXTRA_TVSHOWS, tvShows)
        it.context.startActivity(detailTVShowsIntent)
    }

    inner class TVShowsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImagePoster: ImageView =
            itemView.findViewById(R.id.image_item_recycler_fragment_tvshows_poster)
        var mTextTitle: TextView =
            itemView.findViewById(R.id.text_item_recycler_fragment_tvshows_title)
        var mRatingMovie: RatingBar =
            itemView.findViewById(R.id.rating_item_recycler_fragment_tvshows)
        var mTextRatingScore: TextView =
            itemView.findViewById(R.id.text_item_recycler_fragment_tvshows_rating_score)
        var mTextGenre: TextView =
            itemView.findViewById(R.id.text_item_recycler_fragment_tvshows_genre)
        var mTextRuntime: TextView =
            itemView.findViewById(R.id.text_item_recycler_fragment_tvshows_runtime)
        var mTextNetwork: TextView =
            itemView.findViewById(R.id.text_item_recycler_fragment_tvshows_network)
        var mButtonDetails: TextView =
            itemView.findViewById(R.id.button_item_recycler_fragment_tvshows_details)
    }
}