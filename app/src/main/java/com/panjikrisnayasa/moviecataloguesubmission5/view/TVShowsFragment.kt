package com.panjikrisnayasa.moviecataloguesubmission5.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.panjikrisnayasa.moviecataloguesubmission5.R
import com.panjikrisnayasa.moviecataloguesubmission5.adapter.TVShowsAdapter
import com.panjikrisnayasa.moviecataloguesubmission5.viewmodel.TVShowsViewModel
import kotlinx.android.synthetic.main.fragment_tvshows.*

/**
 * A simple [Fragment] subclass.
 */
class TVShowsFragment : Fragment() {

    private lateinit var mTVShowsAdapter: TVShowsAdapter
    private lateinit var mTVShowsViewModel: TVShowsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tvshows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tContext = context

        showRecyclerView()

        mTVShowsViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(TVShowsViewModel::class.java)

        showLoading(true)
        if (tContext != null)
            mTVShowsViewModel.setTVShows(tContext)

        search_tvshows.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && tContext != null) {
                    if (newText == "") {
                        mTVShowsViewModel.setTVShows(tContext)
                    } else {
                        mTVShowsViewModel.setSearchTVShows(tContext, newText)
                    }
                }
                return false
            }

        })

        mTVShowsViewModel.getTVShows().observe(this.viewLifecycleOwner, Observer { tvShow ->
            if (tvShow != null) {
                mTVShowsAdapter.setData(tvShow)
                showLoading(false)
            }
        })
    }

    private fun showRecyclerView() {
        recycler_fragment_tvshows.setHasFixedSize(true)
        mTVShowsAdapter = TVShowsAdapter()
        mTVShowsAdapter.notifyDataSetChanged()
        recycler_fragment_tvshows.layoutManager = LinearLayoutManager(this.context)
        recycler_fragment_tvshows.adapter = mTVShowsAdapter
        recycler_fragment_tvshows.recycledViewPool.setMaxRecycledViews(0, 0)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progress_fragment_tvshows.visibility = View.VISIBLE
        } else {
            progress_fragment_tvshows.visibility = View.GONE
        }
    }
}
