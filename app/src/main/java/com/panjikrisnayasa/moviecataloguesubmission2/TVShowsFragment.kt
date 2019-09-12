package com.panjikrisnayasa.moviecataloguesubmission2


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.android.synthetic.main.fragment_tvshows.*

/**
 * A simple [Fragment] subclass.
 */
class TVShowsFragment : Fragment() {

    private val mList = arrayListOf<TVShowsModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tvshows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_fragment_tvshows.setHasFixedSize(true)
        mList.addAll(TVShowsObject.getListTVShowsData())
        showRecyclerView()
    }

    private fun showRecyclerView() {
        recycler_fragment_tvshows.layoutManager = LinearLayoutManager(this.context)
        recycler_fragment_tvshows.adapter = TVShowsAdapter(mList)
    }
}
