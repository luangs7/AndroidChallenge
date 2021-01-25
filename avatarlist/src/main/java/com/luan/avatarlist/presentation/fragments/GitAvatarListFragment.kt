package com.luan.avatarlist.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.luan.avatarlist.databinding.FragmentAvatarListBinding
import com.luan.avatarlist.R
import com.luan.avatarlist.domain.model.GitUser
import com.luan.avatarlist.presentation.GitUserViewModel
import com.luan.avatarlist.presentation.adapter.AvatarAdapter
import com.luan.common.base.BaseFragment
import com.luan.common.base.Resource
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GitAvatarListFragment : BaseFragment() {

    private val viewModel : GitUserViewModel by viewModel()
    private var adapter = AvatarAdapter(this::onListClicked)
    private lateinit var binding : FragmentAvatarListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAvatarListBinding>(inflater, R.layout.fragment_avatar_list, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupObservers()
        setupView()
        getItems()
    }

    private fun onListClicked(item:GitUser){
        viewModel.deleteGitUser(item)
    }

    private fun setupView(){
        binding.apply {
            swipeLayout.setOnRefreshListener { getItems() }

            list.apply{
                layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
                adapter = this@GitAvatarListFragment.adapter
            }
        }
    }

    private fun setupObservers(){
        viewModel.getAvatarList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS,
                Resource.Status.CACHE -> {
                    binding.swipeLayout.isRefreshing = false
                    it.data?.let { items-> adapter.items = items.toMutableList() }
                }
                Resource.Status.LOADING -> binding.swipeLayout.isRefreshing = true
                else ->  binding.swipeLayout.isRefreshing = false
            }
        })

        viewModel.deleteUser.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS,
                Resource.Status.CACHE -> {
                    Toast.makeText(requireContext(), getString(R.string.user_removed),
                        Toast.LENGTH_SHORT).show()
                }
                Resource.Status.ERROR -> Toast.makeText(requireContext(), getString(R.string.error_generic),
                    Toast.LENGTH_SHORT).show()
                else -> {}
            }
        })
    }

    private fun getItems(){
        viewModel.getAvatarList()
    }
}