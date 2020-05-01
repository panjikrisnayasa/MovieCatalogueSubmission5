package com.panjikrisnayasa.moviecataloguesubmission5.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.panjikrisnayasa.moviecataloguesubmission5.R
import com.panjikrisnayasa.moviecataloguesubmission5.view.FavoriteMoviesFragment
import com.panjikrisnayasa.moviecataloguesubmission5.view.FavoriteTVShowsFragment

class ViewPagerAdapter(fm: FragmentManager, context: Context?) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var mContext = context

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return FavoriteMoviesFragment()
            1 -> return FavoriteTVShowsFragment()
        }
        return FavoriteMoviesFragment()
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return mContext?.getString(R.string.view_pager_adapter_favorite_movies)
            1 -> return mContext?.getString(R.string.view_pager_adapter_favorite_tv_shows)
        }
        return super.getPageTitle(position)
    }
}