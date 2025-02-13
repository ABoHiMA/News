package com.ae.news.ui.home

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ae.news.R
import com.ae.news.databinding.ActivityHomeBinding
import com.ae.news.models.categories.Category
import com.ae.news.ui.home.fragments.category.CategoryFragment
import com.ae.news.ui.home.fragments.egypt.EgyptNewsFragment
import com.ae.news.ui.home.fragments.news.NewsFragment
import com.ae.news.ui.search.SearchActivity
import com.ae.news.utils.Utils
import com.ae.news.utils.Utils.alertDialog
import com.ae.news.utils.Utils.getDeviceTheme
import com.ae.news.utils.Utils.setLanguage
import com.ae.news.utils.Utils.setMode
import com.ae.news.utils.Utils.sharedPreferences

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var themeItems: Array<String>
    private lateinit var languageItems: Array<String>
    private lateinit var themeAdapter: ArrayAdapter<String>
    private lateinit var languageAdapter: ArrayAdapter<String>
    private lateinit var currentTheme: String
    private lateinit var currentLanguage: String
    private var appBarTitle: String? = null
    private var themePos: Int = 0
    private var languagePos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAppBar()
        initDropDowns()
        startCategoryFragment()
    }

    private fun initAppBar() {
        binding.appBarHome.btnSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity()::class.java)
            startActivity(intent)
        }

        binding.appBarHome.toolBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.navBody.btnHome.setOnClickListener {
            startCategoryFragment()
            binding.drawerLayout.close()
        }

        changeAppBarTitle()
    }

    private fun changeAppBarTitle() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentResumed(fm: FragmentManager, frag: Fragment) {
                super.onFragmentResumed(fm, frag)

                when (frag) {
                    is CategoryFragment -> binding.appBarHome.tbTitle.setText(R.string.home)

                    is NewsFragment -> binding.appBarHome.tbTitle.text =
                        appBarTitle ?: getString(R.string.home)

                    is EgyptNewsFragment -> binding.appBarHome.tbTitle.text =
                        appBarTitle ?: getString(R.string.home)

                    else -> binding.appBarHome.tbTitle.setText(R.string.home)
                }
            }
        }, false)
    }

    private fun initDropDowns() {
        themeItems = resources.getStringArray(R.array.themes)
        languageItems = resources.getStringArray(R.array.languages)

        themePos = sharedPreferences?.getInt(Utils.SAVED_MODE_POS, 0) ?: 0
        languagePos = sharedPreferences?.getInt(Utils.SAVED_LANG_POS, 0) ?: 0

        currentTheme = themeItems[themePos]
        currentLanguage = languageItems[languagePos]

        themeAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, themeItems)
        languageAdapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, languageItems)

        initThemeDropDown()
        initLanguageDropDown()
    }

    private fun initThemeDropDown() {
        binding.navBody.dropdownTheme.setAdapter(themeAdapter)

        binding.navBody.dropdownTheme.setText(currentTheme, false)

        binding.navBody.dropdownTheme.setOnItemClickListener { _, _, position, _ ->
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
                    applyAppTheme(position, newTheme)
                } else {
                    currentTheme = newTheme
                    binding.navBody.dropdownTheme.setText(currentTheme, false)
                    sharedPreferences?.edit()?.putInt(Utils.SAVED_MODE_POS, position)?.apply()
                }
            }
        }
    }

    private fun applyAppTheme(position: Int, newTheme: String) {
        alertDialog(this, getString(R.string.change_theme), {
            currentTheme = newTheme
            setMode(position)
            sharedPreferences?.edit()?.putInt(Utils.SAVED_MODE_POS, position)?.apply()

            finish()
            startActivity(intent)
        }, {
            binding.navBody.dropdownTheme.setText(currentTheme, false)
        }).show()
    }

    private fun initLanguageDropDown() {
        binding.navBody.dropdownLanguage.setAdapter(languageAdapter)

        binding.navBody.dropdownLanguage.setText(currentLanguage, false)

        binding.navBody.dropdownLanguage.setOnItemClickListener { _, _, position, _ ->
            val newLanguage = when (position) {
                0 -> languageItems[0]
                1 -> languageItems[1]
                else -> languageItems[0]
            }
            if (languagePos != position) applyAppLanguage(position, newLanguage)
        }
    }

    private fun applyAppLanguage(position: Int, newLanguage: String) {
        alertDialog(this, getString(R.string.change_language), {
            currentLanguage = newLanguage
            setLanguage(position)
            sharedPreferences?.edit()?.putInt(Utils.SAVED_LANG_POS, position)?.apply()

            finish()
            startActivity(intent)
        }, {
            binding.navBody.dropdownLanguage.setText(currentLanguage, false)
        }).show()
    }

    private fun startCategoryFragment() {
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
        ).replace(
            R.id.fragment_container, CategoryFragment.getInstance(
                onCategoryClickListener = ::onCategoryClick, onEgyClickListener = ::onEgyClick
            ), "Category"
        ).commit()
    }

    private fun onCategoryClick(category: Category) {
        startNewsFragment(category)
        appBarTitle = getString(category.title)
    }

    private fun onEgyClick() {
        startEgyFragment()
        appBarTitle = getString(R.string.menu_egy)
    }

    private fun startNewsFragment(category: Category) {
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
        ).replace(
            R.id.fragment_container, NewsFragment.getInstance(category), "News"
        ).addToBackStack(null).commit()
    }

    private fun startEgyFragment() {
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
        ).replace(
            R.id.fragment_container, EgyptNewsFragment(), "Egy"
        ).addToBackStack(null).commit()
    }
}