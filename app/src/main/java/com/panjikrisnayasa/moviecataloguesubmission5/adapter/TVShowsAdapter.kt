package com.panjikrisnayasa.moviecataloguesubmission5.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.panjikrisnayasa.moviecataloguesubmission5.R
import com.panjikrisnayasa.moviecataloguesubmission5.model.TVShow
import com.panjikrisnayasa.moviecataloguesubmission5.view.DetailMovieTVShowActivity
import kotlinx.android.synthetic.main.item_recycler_fragment_tvshows.view.*

class TVShowsAdapter :
    RecyclerView.Adapter<TVShowsAdapter.TVShowsViewHolder>() {

    companion object {
        private const val BASE_URL = "https://image.tmdb.org/t/p/w185/"
    }

    private var mData = ArrayList<TVShow>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_fragment_tvshows, parent, false)
        return TVShowsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: TVShowsViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    fun setData(tvShows: ArrayList<TVShow>) {
        mData.clear()
        mData.addAll(tvShows)
        notifyDataSetChanged()
    }

    private fun moveToDetail(view: View, tvShowID: String?) {
        val detailIntent =
            Intent(view.context, DetailMovieTVShowActivity::class.java)
        detailIntent.putExtra(DetailMovieTVShowActivity.EXTRA_TVSHOW_ID, tvShowID)
        view.context.startActivity(detailIntent)
    }

    inner class TVShowsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShow: TVShow) {
            with(itemView) {
                image_item_recycler_fragment_tvshows_poster.clipToOutline = true
                val posterPath = BASE_URL + tvShow.posterPath
                Glide.with(context).load(posterPath)
                    .into(image_item_recycler_fragment_tvshows_poster)
                text_item_recycler_fragment_tvshows_name.text = tvShow.name
                val tVoteAverage = tvShow.voteAverage
                var voteAverage = 0f
                if (tVoteAverage != null) {
                    voteAverage = (tVoteAverage / 2).toFloat()
                }
                rating_item_recycler_fragment_tvshows.rating = voteAverage
                text_item_recycler_fragment_tvshows_vote_average.text =
                    tvShow.voteAverage.toString()
                text_item_recycler_fragment_tvshows_popularity.text = tvShow.popularity.toString()
                text_item_recycler_fragment_tvshows_first_air_date.text = tvShow.firstAirDate
                text_item_recycler_fragment_tvshows_language.text = tvShow.language

                button_item_recycler_fragment_tvshows_details.setOnClickListener {
                    moveToDetail(it, tvShow.id)
                }
                itemView.setOnClickListener {
                    moveToDetail(it, tvShow.id)
                }
            }
        }
    }
}