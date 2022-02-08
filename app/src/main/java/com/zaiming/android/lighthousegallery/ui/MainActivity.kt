package com.zaiming.android.lighthousegallery.ui

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
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
    private val needRequestMultiplePermission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        if (it.values.any { value -> value == false }) {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermissionLauncher.launch(needRequestMultiplePermission)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_photos, R.id.navigation_albums, R.id.navigation_selected, R.id.navigation_recommends)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        initBottomNavigationView(navController)

    }

    private fun initBottomNavigationView(navController: NavController) {
        binding.navView.apply {
            labelVisibilityMode = LABEL_VISIBILITY_LABELED
            setupWithNavController(navController)
        }

        //activity listens to different fragments in the foreground
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            TODO()
        }
    }
}