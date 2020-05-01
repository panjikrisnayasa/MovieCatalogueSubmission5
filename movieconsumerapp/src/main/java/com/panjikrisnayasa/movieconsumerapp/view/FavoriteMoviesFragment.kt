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
import com.panjikrisnayasa.movieconsumerapp.adapter.FavoredMoviesAdapter
import com.panjikrisnayasa.movieconsumerapp.db.DatabaseFavoredMoviesContract.FavoredMoviesColumns.Companion.CONTENT_URI_MOVIES
import com.panjikrisnayasa.movieconsumerapp.helper.MappingHelper
import com.panjikrisnayasa.movieconsumerapp.model.Movie
import kotlinx.android.synthetic.main.fragment_favorite_movies.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMoviesFragment : Fragment() {

    companion object {
        private const val EXTRA_STATE = "extra_state"
        private const val EXTRA_TEXT = "extra_text"
    }

    private lateinit var mFavoriteMoviesAdapter: FavoredMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRecyclerView()

        //content provider - start
        val handlerThread = HandlerThread("DataMoviesObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                loadFavoredMoviesAsync()
            }
        }

        activity?.contentResolver?.registerContentObserver(
            CONTENT_URI_MOVIES,
            true,
            myObserver
        )
        //content provider = end

        if (savedInstanceState == null) {
            loadFavoredMoviesAsync()
        } else {
            val text = savedInstanceState.getString(EXTRA_TEXT)
            text_fragment_favorite_movies.text = text
            val list = savedInstanceState.getParcelableArrayList<Movie>(EXTRA_STATE)
            if (list != null) mFavoriteMoviesAdapter.listMovies = list
            if (mFavoriteMoviesAdapter.listMovies.isEmpty()) {
                text_fragment_favorite_movies.visibility = View.VISIBLE
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_TEXT, text_fragment_favorite_movies.text.toString())
        outState.putParcelableArrayList(EXTRA_STATE, mFavoriteMoviesAdapter.listMovies)
    }

    private fun showRecyclerView() {
        recycler_fragment_favorite_movies.setHasFixedSize(true)
        mFavoriteMoviesAdapter =
            FavoredMoviesAdapter()
        recycler_fragment_favorite_movies.layoutManager = LinearLayoutManager(this.context)
        recycler_fragment_favorite_movies.adapter = mFavoriteMoviesAdapter
    }

    private fun loadFavoredMoviesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progress_fragment_favorite_movies.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor =
                    activity?.contentResolver?.query(
                        CONTENT_URI_MOVIES,
                        null,
                        null,
                        null,
                        null
                    )
                MappingHelper.mapFavoredMovieCursorToArrayList(cursor)
            }
            progress_fragment_favorite_movies.visibility = View.INVISIBLE
            val movies = deferredNotes.await()
            if (movies.size > 0) {
                text_fragment_favorite_movies.visibility = View.GONE
                mFavoriteMoviesAdapter.listMovies = movies
            } else {
                mFavoriteMoviesAdapter.listMovies = ArrayList()
                text_fragment_favorite_movies.visibility = View.VISIBLE
            }
        }
    }
}
