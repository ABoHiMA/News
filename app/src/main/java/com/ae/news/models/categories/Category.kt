package com.ae.news.models.categories

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ae.news.R

data class Category(
    val id: String,
    @StringRes
    val title: Int,
    @DrawableRes
    val image: Int,
) {
    companion object {
        fun getCategories(): List<Category> = listOf(
            Category(
                id = "general",
                title = R.string.general,
                image = R.drawable.ic_general,
            ),
            Category(
                id = "business",
                title = R.string.business,
                image = R.drawable.ic_business,
            ),
            Category(
                id = "sports",
                title = R.string.sports,
                image = R.drawable.ic_sports,
            ),
            Category(
                id = "technology",
                title = R.string.technology,
                image = R.drawable.ic_tech,
            ),
            Category(
                id = "science",
                title = R.string.science,
                image = R.drawable.ic_science,
            ),
            Category(
                id = "entertainment",
                title = R.string.entertainment,
                image = R.drawable.ic_entertainment,
            ),
            Category(
                id = "health",
                title = R.string.health,
                image = R.drawable.ic_health,
            ),
            Category(
                id = "",
                title = R.string.menu_egy,
                image = R.drawable.ic_egy,
            ),
        )
    }
}
