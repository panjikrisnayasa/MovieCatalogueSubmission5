package com.panjikrisnayasa.moviecataloguesubmission5.view


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.panjikrisnayasa.moviecataloguesubmission5.R
import com.panjikrisnayasa.moviecataloguesubmission5.adapter.FavoredTVShowsAdapter
import com.panjikrisnayasa.moviecataloguesubmission5.db.FavoredTVShowsHelper
import com.panjikrisnayasa.moviecataloguesubmission5.helper.MappingHelper
import com.panjikrisnayasa.moviecataloguesubmission5.model.TVShow
import kotlinx.android.synthetic.main.fragment_favorite_tvshows.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTVShowsFragment : Fragment() {

    companion object {
        private const val EXTRA_STATE = "extra_state"
        private const val EXTRA_TEXT = "extra_text"
    }

    private lateinit var mFavoriteTVShowsAdapter: FavoredTVShowsAdapter
    private lateinit var mFavoredTVShowsHelper: FavoredTVShowsHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tvshows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRecyclerView()

        mFavoredTVShowsHelper = FavoredTVShowsHelper.getInstance(this.requireActivity().applicationContext)
        mFavoredTVShowsHelper.open()

        if (savedInstanceState == null) {
            loadFavoredTVShowsAsync()
        } else {
            val text = savedInstanceState.getString(EXTRA_TEXT)
            text_fragment_favorite_tvshow.text = text
            val list = savedInstanceState.getParcelableArrayList<TVShow>(EXTRA_STATE)
            if (list != null) mFavoriteTVShowsAdapter.listTVShows = list
            if (mFavoriteTVShowsAdapter.listTVShows.isEmpty()) {
                text_fragment_favorite_tvshow.visibility = View.VISIBLE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (requestCode == DetailFavoredMovieTVShowActivity.REQUEST_UPDATE) {
                if (resultCode == DetailFavoredMovieTVShowActivity.RESULT_ADD) {
                }
                if (resultCode == DetailFavoredMovieTVShowActivity.RESULT_DELETE) {
                    val position =
                        data.getIntExtra(DetailFavoredMovieTVShowActivity.EXTRA_POSITION, 0)
                    mFavoriteTVShowsAdapter.removeItem(position)
                    if (mFavoriteTVShowsAdapter.listTVShows.size == 0) {
                        text_fragment_favorite_tvshow.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_TEXT, text_fragment_favorite_tvshow.text.toString())
        outState.putParcelableArrayList(EXTRA_STATE, mFavoriteTVShowsAdapter.listTVShows)
    }

    override fun onDestroy() {
        super.onDestroy()
        mFavoredTVShowsHelper.close()
    }

    private fun showRecyclerView() {
        recycler_fragment_favorite_tvshow.setHasFixedSize(true)
        mFavoriteTVShowsAdapter = FavoredTVShowsAdapter(this)
        recycler_fragment_favorite_tvshow.layoutManager = LinearLayoutManager(this.context)
        recycler_fragment_favorite_tvshow.adapter = mFavoriteTVShowsAdapter
    }

    private fun loadFavoredTVShowsAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progress_fragment_favorite_tvshow.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = mFavoredTVShowsHelper.queryAll()
                MappingHelper.mapFavoredTVShowCursorToArrayList(cursor)
            }
            progress_fragment_favorite_tvshow.visibility = View.INVISIBLE
            val tvShow = deferredNotes.await()
            if (tvShow.size > 0) {
                mFavoriteTVShowsAdapter.listTVShows = tvShow
                text_fragment_favorite_tvshow.visibility = View.GONE
            } else {
                mFavoriteTVShowsAdapter.listTVShows = ArrayList()
                text_fragment_favorite_tvshow.visibility = View.VISIBLE
            }
        }
    }
}
