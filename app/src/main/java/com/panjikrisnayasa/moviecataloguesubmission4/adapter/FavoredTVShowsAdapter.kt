package com.panjikrisnayasa.moviecataloguesubmission4.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panjikrisnayasa.moviecataloguesubmission4.R
import com.panjikrisnayasa.moviecataloguesubmission4.model.TVShow
import com.panjikrisnayasa.moviecataloguesubmission4.view.DetailFavoredMovieTVShowActivity
import com.panjikrisnayasa.moviecataloguesubmission4.view.FavoriteTVShowsFragment
import kotlinx.android.synthetic.main.item_recycler_fragment_tvshows.view.*

class FavoredTVShowsAdapter(private val fragment: FavoriteTVShowsFragment) :
    RecyclerView.Adapter<FavoredTVShowsAdapter.FavoredTVShowsHolder>() {

    var listTVShows = ArrayList<TVShow>()
        set(listTVShows) {
            if (listTVShows.size > 0) {
                this.listTVShows.clear()
            }
            this.listTVShows.addAll(listTVShows)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoredTVShowsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_fragment_tvshows, parent, false)
        return FavoredTVShowsHolder(view)
    }

    override fun getItemCount(): Int {
        return this.listTVShows.size
    }

    override fun onBindViewHolder(holder: FavoredTVShowsHolder, position: Int) {
        holder.bind(listTVShows[position])
    }

    fun removeItem(position: Int) {
        this.listTVShows.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listTVShows.size)
    }

    private fun moveToDetail(position: Int, tvShow: TVShow) {
        val detailIntent =
            Intent(fragment.context, DetailFavoredMovieTVShowActivity::class.java)
        detailIntent.putExtra(DetailFavoredMovieTVShowActivity.EXTRA_TVSHOW, tvShow)
        detailIntent.putExtra(DetailFavoredMovieTVShowActivity.EXTRA_POSITION, position)
        fragment.startActivityForResult(
            detailIntent,
            DetailFavoredMovieTVShowActivity.REQUEST_UPDATE
        )
    }

    inner class FavoredTVShowsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShow: TVShow) {
            with(itemView) {
                image_item_recycler_fragment_tvshows_poster.clipToOutline = true
                val posterPath = "https://image.tmdb.org/t/p/w185/" + tvShow.posterPath
                Glide.with(context).load(posterPath)
                    .into(image_item_recycler_fragment_tvshows_poster)
                text_item_recycler_fragment_tvshows_name.text = tvShow.name
                val voteAverage = (tvShow.voteAverage?.div(2))?.toFloat()
                if (voteAverage != null) {
                    rating_item_recycler_fragment_tvshows.rating = voteAverage
                }
                text_item_recycler_fragment_tvshows_vote_average.text =
                    tvShow.voteAverage.toString()
                text_item_recycler_fragment_tvshows_popularity.text = tvShow.popularity.toString()
                text_item_recycler_fragment_tvshows_first_air_date.text = tvShow.firstAirDate
                text_item_recycler_fragment_tvshows_language.text = tvShow.language

                button_item_recycler_fragment_tvshows_details.setOnClickListener {
                    moveToDetail(adapterPosition, tvShow)
                }
                itemView.setOnClickListener {
                    moveToDetail(adapterPosition, tvShow)
                }
            }
        }
    }

}