package com.panjikrisnayasa.moviecataloguesubmission2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
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
        when(position) {
            0 -> return "Movies"
            1 -> return "TV Shows"
        }
        return super.getPageTitle(position)
    }
}