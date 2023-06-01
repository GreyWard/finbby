package com.example.finbbyapp

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finbbyapp.databinding.ActivityMainBinding
import com.example.finbbyapp.ui.AddContent1Fragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var nav: Number = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_forum, R.id.navigation_add_content
            )
        )
        var fragment = AddContent1Fragment()
        val currentFragment = getSupportFragmentManager()?.findFragmentByTag(AddContent1Fragment::class.java.simpleName)
        if(currentFragment !is AddContent1Fragment) {
            getSupportActionBar()?.title="tes"
        }
        val onDestinationChangedListener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_forum -> {
                    nav = 1
                    // Mengatur option menu untuk Fragment 1
                    // Panggil invalidateOptionsMenu() untuk memperbarui menu
                    invalidateOptionsMenu()
                }
                R.id.navigation_home -> {
                    nav = 0
                    // Mengatur option menu untuk Fragment 2
                    invalidateOptionsMenu()
                }
                // Tambahkan tujuan Fragment lainnya
            }
        }

        navController.addOnDestinationChangedListener(onDestinationChangedListener)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        if(nav == 0) {
            inflater.inflate(R.menu.search_menu, menu)

            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            val searchView = menu.findItem(R.id.search).actionView as SearchView

            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.queryHint = "search content..."
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String): Boolean {
//                mainViewModel.findUser(query)
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return false
                }
            })
        } else if(nav == 1) {
            inflater.inflate(R.menu.forum_menu, menu)
        }

        return true
    }

}