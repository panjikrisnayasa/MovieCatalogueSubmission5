package com.panjikrisnayasa.moviecataloguesubmission3.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.panjikrisnayasa.moviecataloguesubmission3.R
import com.panjikrisnayasa.moviecataloguesubmission3.adapter.MoviesAdapter
import com.panjikrisnayasa.moviecataloguesubmission3.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movies.*

/**
 * A simple [Fragment] subclass.
 */
class MoviesFragment : Fragment() {

    private lateinit var mMoviesAdapter: MoviesAdapter
    private lateinit var mMoviesViewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showRecyclerView()

        mMoviesViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MoviesViewModel::class.java)

        showLoading(true)
        mMoviesViewModel.setMovies(this.context!!)

        mMoviesViewModel.getMovies().observe(this.viewLifecycleOwner, Observer { movies ->
            if (movies != null) {
                showLoading(false)
                mMoviesAdapter.setData(movies)
            }
        })
    }

    private fun showRecyclerView() {
        recycler_fragment_movies.setHasFixedSize(true)
        mMoviesAdapter =
            MoviesAdapter()
        mMoviesAdapter.notifyDataSetChanged()
        recycler_fragment_movies.layoutManager = LinearLayoutManager(this.context)
        recycler_fragment_movies.adapter = mMoviesAdapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progress_fragment_movies.visibility = View.VISIBLE
        } else {
            progress_fragment_movies.visibility = View.GONE
        }
    }
}
