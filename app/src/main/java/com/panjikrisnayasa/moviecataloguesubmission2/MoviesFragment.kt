package com.panjikrisnayasa.moviecataloguesubmission2


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_movies.*

/**
 * A simple [Fragment] subclass.
 */
class MoviesFragment : Fragment() {

    private val mList = arrayListOf<MoviesModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_fragment_movies.setHasFixedSize(true)
        mList.addAll(MoviesObject.getListMoviesData())
        showRecyclerView()
    }

    private fun showRecyclerView() {
        recycler_fragment_movies.layoutManager = LinearLayoutManager(this.context)
        recycler_fragment_movies.adapter = MoviesAdapter(mList)
    }
}
