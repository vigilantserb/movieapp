package com.stameni.com.movieapp.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.stameni.com.movieapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewpager_main.adapter = VideoTypeAdapter(supportFragmentManager)
        tabs_main.setupWithViewPager(viewpager_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_movies, menu)
        return true
    }
}
