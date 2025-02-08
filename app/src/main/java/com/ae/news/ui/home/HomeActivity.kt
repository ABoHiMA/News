package com.ae.news.ui.home

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ae.news.R
import com.ae.news.databinding.ActivityHomeBinding
import com.ae.news.models.categories.Category
import com.ae.news.ui.home.fragments.category.CategoryFragment
import com.ae.news.ui.home.fragments.news.NewsFragment
import com.ae.news.ui.search.SearchActivity
import com.ae.news.utils.Utils
import com.ae.news.utils.Utils.alertDialog
import com.ae.news.utils.Utils.getDeviceTheme
import com.ae.news.utils.Utils.initApp
import com.ae.news.utils.Utils.setLanguage
import com.ae.news.utils.Utils.setMode
import com.ae.news.utils.Utils.sharedPreferences

class HomeActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        initApp(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initAppBar()
        startCategoryFragment()
    }

    private fun initAppBar() {
        viewBinding.appBarHome.btnSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity()::class.java)
            startActivity(intent)
        }

        viewBinding.appBarHome.toolBar.setNavigationOnClickListener {
            initDrawer()
            viewBinding.drawerLayout.open()
        }

        viewBinding.btnHome.setOnClickListener {
            startCategoryFragment()
            viewBinding.drawerLayout.close()
        }

    }

    private fun initDrawer() {
        val themeItems = resources.getStringArray(R.array.themes)
        val languageItems = resources.getStringArray(R.array.languages)

        val themePos = sharedPreferences?.getInt(Utils.SAVED_MODE_POS, 0) ?: 0
        val languagePos = sharedPreferences?.getInt(Utils.SAVED_LANG_POS, 0) ?: 0

        var currentTheme = themeItems[themePos]
        var currentLanguage = languageItems[languagePos]

        val themeAdapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, themeItems)
        val languageAdapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, languageItems)

        viewBinding.dropdownTheme.setAdapter(themeAdapter)
        viewBinding.dropdownLanguage.setAdapter(languageAdapter)

        viewBinding.dropdownTheme.setText(currentTheme, false)
        viewBinding.dropdownLanguage.setText(currentLanguage, false)

        viewBinding.dropdownTheme.setOnItemClickListener { _, _, position, _ ->
            val newTheme = when (position) {
                0 -> themeItems[0]
                1 -> themeItems[1]
                2 -> themeItems[2]
                else -> themeItems[0]
            }
            if (themePos != position) {
                val modeFlag = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                val isDarkModeOn = modeFlag == Configuration.UI_MODE_NIGHT_YES
                if ((position == 0 && isDarkModeOn && getDeviceTheme(this) == 1) || (position == 0 && !isDarkModeOn && getDeviceTheme(
                        this
                    ) == 2) || (position == 1 && isDarkModeOn) || (position == 2 && (!isDarkModeOn))
                ) {
                    alertDialog(this, getString(R.string.change_theme), {
                        currentTheme = newTheme
                        setMode(position)
                        sharedPreferences?.edit()?.putInt(Utils.SAVED_MODE_POS, position)?.apply()

                        finish()
                        startActivity(intent)
                    }, {
                        viewBinding.dropdownTheme.setText(currentTheme, false)
                    }).show()
                } else {
                    currentTheme = newTheme
                    viewBinding.dropdownTheme.setText(currentTheme, false)
                    sharedPreferences?.edit()?.putInt(Utils.SAVED_MODE_POS, position)?.apply()
                }
            }
        }

        viewBinding.dropdownLanguage.setOnItemClickListener { _, _, position, _ ->
            val newLanguage = when (position) {
                0 -> languageItems[0]
                1 -> languageItems[1]
                else -> languageItems[0]
            }
            if (languagePos != position) {
                alertDialog(this, getString(R.string.change_language), {
                    currentLanguage = newLanguage
                    setLanguage(this, position)
                    sharedPreferences?.edit()?.putInt(Utils.SAVED_LANG_POS, position)?.apply()

                    finish()
                    startActivity(intent)
                }, {
                    viewBinding.dropdownLanguage.setText(currentLanguage, false)
                }).show()
            }
        }
    }

    private fun startCategoryFragment() {
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.exit_to_left,
            R.anim.enter_from_right,
            R.anim.exit_to_right,
            R.anim.enter_from_left,
        ).replace(
            R.id.fragment_container, CategoryFragment.getInstance(
                onCategoryClickListener = ::onCategoryClick
            )
        ).commit()
    }

    private fun onCategoryClick(category: Category) {
        startNewsFragment(category)
        viewBinding.appBarHome.tbTitle.setText(category.title)
    }

    private fun startNewsFragment(category: Category) {
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right,
        ).replace(
            R.id.fragment_container, NewsFragment.getInstance(category)
        ).addToBackStack(null).commit()
    }

}