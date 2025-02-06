package com.ae.news.ui.home.fragments.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ae.news.databinding.FragmentCategoryBinding
import com.ae.news.models.categories.Category

class CategoryFragment : Fragment() {
    private lateinit var viewBinding: FragmentCategoryBinding
    private lateinit var adapter: CategoryAdapter
    private var onCategoryClickListener: OnCategoryClickListener? = null

    fun interface OnCategoryClickListener {
        fun onCategoryClick(category: Category)
    }

    companion object {
        fun getInstance(
            onCategoryClickListener: OnCategoryClickListener
        ): CategoryFragment {
            val fragment = CategoryFragment()
            fragment.onCategoryClickListener = onCategoryClickListener
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentCategoryBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        adapter = CategoryAdapter(
            onClick = ::onCategoryClick
        )
        viewBinding.rvCat.adapter = adapter
    }

    private fun onCategoryClick(category: Category) {
        onCategoryClickListener?.onCategoryClick(category)
    }
}