package com.panjikrisnayasa.moviecataloguesubmission4.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.panjikrisnayasa.moviecataloguesubmission4.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_main_bottom_navigation_movies -> {
                    replaceFragment(MoviesFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.menu_main_bottom_navigation_tv_shows -> {
                    replaceFragment(TVShowsFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.menu_main_bottom_navigation_favorite -> {
                    replaceFragment(FavoriteFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            return@OnNavigationItemSelectedListener false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(MoviesFragment())
        bottom_navigation_view_main.setOnNavigationItemSelectedListener(
            mOnNavigationItemSelectedListener
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_change_language, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.navigation_menu_main_change_language -> {
                val changeLanguageIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(changeLanguageIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_main_fragment, fragment, fragment::class.java.simpleName)
            .commit()
    }
}
