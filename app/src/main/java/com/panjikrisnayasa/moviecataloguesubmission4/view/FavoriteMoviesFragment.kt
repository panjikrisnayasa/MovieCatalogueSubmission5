package com.panjikrisnayasa.moviecataloguesubmission4.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.panjikrisnayasa.moviecataloguesubmission4.R
import com.panjikrisnayasa.moviecataloguesubmission4.adapter.MoviesAdapter
import com.panjikrisnayasa.moviecataloguesubmission4.db.FavoredMoviesHelper
import com.panjikrisnayasa.moviecataloguesubmission4.helper.MappingHelper
import com.panjikrisnayasa.moviecataloguesubmission4.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_favorite_movies.*
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMoviesFragment : Fragment() {

    private lateinit var mFavoriteMoviesAdapter: MoviesAdapter
    private lateinit var mFavoredMoviesHelper: FavoredMoviesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        showRecyclerView()
//
//        mFavoredMoviesHelper = FavoredMoviesHelper.getInstance(this.activity!!.applicationContext)
//        mFavoredMoviesHelper.open()
//
//        loadFavoredMoviesAsync()
//    }

//    override fun onDestroy() {
//        super.onDestroy()
//        mFavoredMoviesHelper.close()
//    }

//    private fun showRecyclerView() {
//        recycler_fragment_favorite_movies.setHasFixedSize(true)
//        mFavoriteMoviesAdapter = MoviesAdapter(this.activity)
//        recycler_fragment_favorite_movies.layoutManager = LinearLayoutManager(this.context)
//        recycler_fragment_favorite_movies.adapter = mFavoriteMoviesAdapter
//    }

//    private fun loadFavoredMoviesAsync() {
//        GlobalScope.launch(Dispatchers.Main) {
//            progress_fragment_favorite_movies.visibility = View.VISIBLE
//            val deferredNotes = async(Dispatchers.IO) {
//                val cursor = mFavoredMoviesHelper.queryAll()
//                MappingHelper.mapFavoredMovieCursorToArrayList(cursor)
//            }
//            progress_fragment_favorite_movies.visibility = View.INVISIBLE
//            val movies = deferredNotes.await()
//            if (movies.size > 0) {
//                mFavoriteMoviesAdapter.mData = movies
//            } else {
//                mFavoriteMoviesAdapter.mData= ArrayList()
//            }
//        }
//    }
}
