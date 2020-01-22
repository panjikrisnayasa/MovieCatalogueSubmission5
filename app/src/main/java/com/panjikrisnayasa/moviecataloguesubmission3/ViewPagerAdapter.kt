package com.panjikrisnayasa.moviecataloguesubmission3

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm: FragmentManager, context: Context) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var mContext = context

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return MoviesFragment()
            1 -> return TVShowsFragment()
        }
        return MoviesFragment()
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return mContext.getString(R.string.view_pager_adapter_movies)
            1 -> return mContext.getString(R.string.view_pager_adapter_tv_shows)
        }
        return super.getPageTitle(position)
    }
}