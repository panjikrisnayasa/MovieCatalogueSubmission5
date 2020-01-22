package com.panjikrisnayasa.moviecataloguesubmission3

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_recycler_fragment_tvshows.view.*

class TVShowsAdapter : RecyclerView.Adapter<TVShowsAdapter.TVShowsViewHolder>() {

    private val mData = ArrayList<TVShow>()

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
        val detailMoviesIntent = Intent(view.context, DetailMovieTVShowActivity::class.java)
        detailMoviesIntent.putExtra(DetailMovieTVShowActivity.EXTRA_TVSHOWS, tvShowID)
        view.context.startActivity(detailMoviesIntent)
    }

    inner class TVShowsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShow: TVShow) {
            with(itemView) {
                image_item_recycler_fragment_tvshows_poster.clipToOutline = true
                val posterPath = "https://image.tmdb.org/t/p/w185/" + tvShow.posterPath
                Glide.with(context).load(posterPath)
                    .into(image_item_recycler_fragment_tvshows_poster)
                text_item_recycler_fragment_tvshows_name.text = tvShow.name
                rating_item_recycler_fragment_tvshows.rating = (tvShow.voteAverage!! / 2).toFloat()
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