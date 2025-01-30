package com.ae.news.ui.home.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ae.news.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {
    lateinit var viewBinding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentNewsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

}