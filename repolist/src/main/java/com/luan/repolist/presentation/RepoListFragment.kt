package com.luan.repolist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luan.common.base.BaseFragment
import com.luan.common.base.Resource
import com.luan.repolist.R
import com.luan.repolist.databinding.FragmentRepoListBinding
import com.luan.repolist.presentation.adapter.RepositoriesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepoListFragment: BaseFragment() {

    private val viewModel : RepoListViewModel by viewModel()
    private var adapter = RepositoriesAdapter()
    private lateinit var binding : FragmentRepoListBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentRepoListBinding>(inflater, R.layout.fragment_repo_list, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupView()
        setupObservers()
        getItems()
    }

    private fun setupView(){
        binding.apply {
            list.apply{
                val lm = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                layoutManager = lm
                adapter = this@RepoListFragment.adapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        val lastPosition = lm.findLastVisibleItemPosition()
                        if (lastPosition >= (recyclerView.adapter?.itemCount?.minus(10)!!)) {
                            getItems()
                        }
                    }
                })
            }
        }
    }

    private fun setupObservers(){
        viewModel.getRepositoryResponse.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS,
                Resource.Status.CACHE -> it.data?.let { items-> adapter.items = items.toMutableList() }
                else ->  {}
            }
        })
    }

    private fun getItems(){
        viewModel.getList()
    }
}