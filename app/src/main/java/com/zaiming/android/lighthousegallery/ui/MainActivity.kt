package com.zaiming.android.lighthousegallery.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView.LABEL_VISIBILITY_LABELED
import com.zaiming.android.lighthousegallery.R
import com.zaiming.android.lighthousegallery.databinding.ActivityMainBinding

/**
 * @author zaiming
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_photos, R.id.navigation_albums, R.id.navigation_selected, R.id.navigation_recommends)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.labelVisibilityMode = LABEL_VISIBILITY_LABELED
        navView.setupWithNavController(navController)
    }
}