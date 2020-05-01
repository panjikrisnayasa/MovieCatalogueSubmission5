package com.panjikrisnayasa.movieconsumerapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.panjikrisnayasa.movieconsumerapp.R
import com.panjikrisnayasa.movieconsumerapp.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.elevation = 0f

        view_pager_fragment_favorite.adapter =
            ViewPagerAdapter(
                supportFragmentManager,
                applicationContext
            )
        tab_fragment_favorite.setupWithViewPager(view_pager_fragment_favorite)
    }
}
