package com.luan.emojilist.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.luan.common.base.BaseFragment
import com.luan.common.base.Resource
import com.luan.common.extension.handleLoading
import com.luan.common.extension.setImage
import com.luan.emojilist.R
import com.luan.emojilist.databinding.FragmentEmojiHomeBinding
import com.luan.emojilist.databinding.FragmentEmojiListBinding
import com.luan.emojilist.presentation.EmojiListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmojiHomeFragment : BaseFragment() {

    private lateinit var binding : FragmentEmojiHomeBinding
    private val mainViewModel : EmojiListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentEmojiHomeBinding>(inflater, R.layout.fragment_emoji_home, container, false).also {
            it.viewModel = mainViewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupObserver()
        mainViewModel.getRandomEmoji()

//        btnListEmoji.setOnClickListener { findNavController().navigate(EmojiHomeFragmentDirections.actionHomeToList()) }
    }

    private fun setupObserver(){
        mainViewModel.getRandomEmojiResource.observe(viewLifecycleOwner, Observer {
            handleLoading(it.status,binding.progressBar)
            when(it.status){
                Resource.Status.SUCCESS,
                Resource.Status.CACHE -> binding.emoji.setImage(it.data?.source)
                else -> {}
            }
        })

        mainViewModel.getListEmojiResource.observe(viewLifecycleOwner, Observer {
            handleLoading(it.status,binding.progressBar)
        })
    }
}