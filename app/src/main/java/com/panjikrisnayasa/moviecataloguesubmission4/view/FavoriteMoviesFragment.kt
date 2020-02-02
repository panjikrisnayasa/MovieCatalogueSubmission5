package com.panjikrisnayasa.moviecataloguesubmission4.view


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.panjikrisnayasa.moviecataloguesubmission4.R
import com.panjikrisnayasa.moviecataloguesubmission4.adapter.FavoredMoviesAdapter
import com.panjikrisnayasa.moviecataloguesubmission4.db.FavoredMoviesHelper
import com.panjikrisnayasa.moviecataloguesubmission4.helper.MappingHelper
import com.panjikrisnayasa.moviecataloguesubmission4.model.Movie
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
    }

    private lateinit var mFavoriteMoviesAdapter: FavoredMoviesAdapter
    private lateinit var mFavoredMoviesHelper: FavoredMoviesHelper

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

        mFavoredMoviesHelper = FavoredMoviesHelper.getInstance(this.activity!!.applicationContext)
        mFavoredMoviesHelper.open()

        if (savedInstanceState == null) {
            loadFavoredMoviesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Movie>(EXTRA_STATE)
            if (list != null) mFavoriteMoviesAdapter.listMovies = list
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
                    mFavoriteMoviesAdapter.removeItem(position)
                    if (mFavoriteMoviesAdapter.listMovies.size == 0) {
                        text_fragment_favorite_movies.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, mFavoriteMoviesAdapter.listMovies)
    }

    override fun onDestroy() {
        super.onDestroy()
        mFavoredMoviesHelper.close()
    }

    private fun showRecyclerView() {
        recycler_fragment_favorite_movies.setHasFixedSize(true)
        mFavoriteMoviesAdapter = FavoredMoviesAdapter(this)
        recycler_fragment_favorite_movies.layoutManager = LinearLayoutManager(this.context)
        recycler_fragment_favorite_movies.adapter = mFavoriteMoviesAdapter
    }

    private fun loadFavoredMoviesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            progress_fragment_favorite_movies.visibility = View.VISIBLE
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = mFavoredMoviesHelper.queryAll()
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
