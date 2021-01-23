package com.luan.emojilist.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.luan.common.base.BaseFragment
import com.luan.common.base.Resource
import com.luan.common.extension.handleLoading
import com.luan.common.extension.setImage
import com.luan.emojilist.R
import com.luan.emojilist.databinding.FragmentHomeBinding
import com.luan.emojilist.presentation.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmojiHomeFragment : BaseFragment() {

    private lateinit var binding : FragmentHomeBinding
    private val mainViewModel : MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false).also {
            it.viewModel = mainViewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupObserver()
        mainViewModel.getRandomEmoji()

        btnListEmoji.setOnClickListener { findNavController().navigate(EmojiHomeFragmentDirections.actionHomeToList()) }
    }

    private fun setupObserver(){
        mainViewModel.getRandomEmojiResource.observe(viewLifecycleOwner, Observer {
            handleLoading(it.status,progressBar)
            when(it.status){
                Resource.Status.SUCCESS,
                Resource.Status.CACHE -> emoji.setImage(it.data?.source)
                else -> {}
            }
        })

        mainViewModel.getListEmojiResource.observe(viewLifecycleOwner, Observer {
            handleLoading(it.status,progressBar)
        })
    }
}