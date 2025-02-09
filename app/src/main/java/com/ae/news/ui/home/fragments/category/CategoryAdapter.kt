package com.ae.news.ui.home.fragments.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ae.news.databinding.ItemCategoryBinding
import com.ae.news.models.categories.Category

class CategoryAdapter(
    private val categories: List<Category> = Category.getCategories(),
    val onClick: ((category: Category) -> Unit),
    val onEgyClick: (() -> Unit),
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(categories[position])
        if (position != categories.size - 1) {
            holder.itemBinding.btnCat.setOnClickListener {
                onClick(categories[position])
            }
        } else {
            holder.itemBinding.btnCat.setOnClickListener {
                onEgyClick()
            }
        }
    }

    inner class ViewHolder(val itemBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(category: Category) {
            itemBinding.imgCat.setImageResource(category.image)
            itemBinding.txtCat.setText(category.title)
        }
    }

}
