package com.luan.avatarlist.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.luan.avatarlist.R
import com.luan.avatarlist.databinding.FragmentAvatarHomeBinding
import com.luan.avatarlist.domain.interactor.GitUserUseCase
import com.luan.avatarlist.presentation.GitUserViewModel
import com.luan.common.base.BaseFragment
import com.luan.common.base.Resource
import com.luan.common.extension.handleLoading
import com.luan.common.extension.setImage

import org.koin.androidx.viewmodel.ext.android.viewModel

class GitUserHomeFragment : BaseFragment() {

    private lateinit var binding : FragmentAvatarHomeBinding
    private val viewModel : GitUserViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAvatarHomeBinding>(inflater, R.layout.fragment_avatar_home, container, false).also {
            it.vm = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupObserver()
        setupView()
    }

    private fun setupView(){
        binding.apply {
            btnList.setOnClickListener { findNavController().navigate(GitUserHomeFragmentDirections.actionAvatarHomeToAvatarList()) }
            btnRepo.setOnClickListener { findNavController().navigate(GitUserHomeFragmentDirections.actionAvatarHomeToRepoList()) }
        }
    }

    private fun setupObserver(){
        viewModel.getGitUser.observe(viewLifecycleOwner, Observer {
            handleLoading(it.status,binding.progressbar)
            when(it.status){
                Resource.Status.SUCCESS,
                Resource.Status.CACHE -> Toast.makeText(requireContext(), getString(R.string.gituser_stored),Toast.LENGTH_SHORT).show()
                Resource.Status.ERROR -> {
                    val message = if(it.exception is GitUserUseCase.GitUserNotFoundException) getString(R.string.not_found)
                    else  getString(R.string.error_generic) +  it.exception

                    Toast.makeText(requireContext(),message ,Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        })
    }
}