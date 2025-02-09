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
    private var onEgyClickListener: OnEgyClickListener? = null

    fun interface OnCategoryClickListener {
        fun onCategoryClick(category: Category)
    }

    fun interface OnEgyClickListener {
        fun onEgyClick()
    }

    companion object {
        fun getInstance(
            onCategoryClickListener: OnCategoryClickListener,
            onEgyClickListener: OnEgyClickListener
        ): CategoryFragment {
            val fragment = CategoryFragment()
            fragment.onCategoryClickListener = onCategoryClickListener
            fragment.onEgyClickListener = onEgyClickListener
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
            onClick = ::onCategoryClick,
            onEgyClick = ::onEgyClick,
        )
        viewBinding.rvCat.adapter = adapter
    }

    private fun onCategoryClick(category: Category) {
        onCategoryClickListener?.onCategoryClick(category)
    }

    private fun onEgyClick() {
        onEgyClickListener?.onEgyClick()
    }
}