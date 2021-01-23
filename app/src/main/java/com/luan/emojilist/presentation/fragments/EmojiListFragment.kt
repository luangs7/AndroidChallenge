package com.luan.emojilist.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.luan.common.base.BaseFragment
import com.luan.common.base.Resource
import com.luan.emojilist.R
import com.luan.emojilist.databinding.FragmentEmojiListBinding
import com.luan.emojilist.databinding.FragmentHomeBinding
import com.luan.emojilist.presentation.MainViewModel
import com.luan.emojilist.presentation.adapter.EmojiAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EmojiListFragment : BaseFragment() {

    private val mainViewModel : MainViewModel by sharedViewModel()
    private var adapter: EmojiAdapter? =  null
    private lateinit var binding : FragmentEmojiListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentEmojiListBinding>(inflater, R.layout.fragment_emoji_list, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = EmojiAdapter {
            adapter?.removeAt(it)
        }

        setupObservers()
        setupView()
        getItems()
    }

    private fun setupView(){
        binding.apply {
            swipeLayout.setOnRefreshListener { getItems() }

            list.apply{
                layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
                adapter = this@EmojiListFragment.adapter
            }
        }
    }

    private fun setupObservers(){
        mainViewModel.getListEmojiResource.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS,
                Resource.Status.CACHE -> {
                    binding.swipeLayout.isRefreshing = false
                    it.data?.let { items-> adapter?.items = items.toMutableList() }
                }
                Resource.Status.LOADING -> binding.swipeLayout.isRefreshing = true
                else ->  binding.swipeLayout.isRefreshing = false
            }
        })
    }

    private fun getItems(){
        mainViewModel.getList()
    }
}