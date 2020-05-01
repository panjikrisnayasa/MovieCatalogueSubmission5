package com.panjikrisnayasa.movieconsumerapp.view


import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.panjikrisnayasa.movieconsumerapp.R
import com.panjikrisnayasa.movieconsumerapp.adapter.FavoredTVShowsAdapter
import com.panjikrisnayasa.movieconsumerapp.db.DatabaseFavoredTVShowsContract.FavoredTVShowsColumns.Companion.CONTENT_URI_TV_SHOWS
import com.panjikrisnayasa.movieconsumerapp.helper.MappingHelper
import com.panjikrisnayasa.movieconsumerapp.model.TVShow
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

        //content provider - start
        val handlerThread = HandlerThread("DataTVShowsObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                loadFavoredTVShowsAsync()
            }
        }

        activity?.contentResolver?.registerContentObserver(
            CONTENT_URI_TV_SHOWS,
            true,
            myObserver
        )
        //content provider = end

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_TEXT, text_fragment_favorite_tvshow.text.toString())
        outState.putParcelableArrayList(EXTRA_STATE, mFavoriteTVShowsAdapter.listTVShows)
    }

    private fun showRecyclerView() {
        recycler_fragment_favorite_tvshow.setHasFixedSize(true)
        mFavoriteTVShowsAdapter =
            FavoredTVShowsAdapter()
        recycler_fragment_favorite_tvshow.layoutManager = LinearLayoutManager(this.context)
        recycler_fragment_favorite_tvshow.adapter = mFavoriteTVShowsAdapter
    }

    private fun loadFavoredTVShowsAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progress_fragment_favorite_tvshow.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor =
                    activity?.contentResolver?.query(
                        CONTENT_URI_TV_SHOWS,
                        null,
                        null,
                        null,
                        null
                    )
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
