package com.ae.news.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ae.news.R
import com.ae.news.databinding.ActivityHomeBinding
import com.ae.news.models.categories.Category
import com.ae.news.ui.home.fragments.category.CategoryFragment
import com.ae.news.ui.home.fragments.news.NewsFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        startCategoryFragment()

        viewBinding.appBarHome.toolBar.setNavigationOnClickListener {
            viewBinding.drawerLayout.open()
        }

        viewBinding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> startCategoryFragment()
                else -> startCategoryFragment()
            }

            viewBinding.drawerLayout.close()
            return@setNavigationItemSelectedListener true
        }
    }

    private fun startCategoryFragment() {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.exit_to_left,
                R.anim.enter_from_right,
                R.anim.exit_to_right,
                R.anim.enter_from_left,
            )
            .replace(
                R.id.fragment_container, CategoryFragment.getInstance(
                    onCategoryClickListener = ::onCategoryClick
                )
            )
            .commit()
    }

    private fun onCategoryClick(category: Category) {
        startNewsFragment(category)
        viewBinding.appBarHome.tbTitle.setText(category.title)
    }

    private fun startNewsFragment(category: Category) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right,
            )
            .replace(
                R.id.fragment_container, NewsFragment.getInstance(category)
            )
            .addToBackStack(null)
            .commit()
    }

}