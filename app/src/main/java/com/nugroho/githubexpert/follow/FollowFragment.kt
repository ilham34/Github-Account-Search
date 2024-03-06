package com.nugroho.githubexpert.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nugroho.githubexpert.R
import com.nugroho.githubexpert.core.data.Resource
import com.nugroho.githubexpert.core.ui.FollowUserAdapter
import com.nugroho.githubexpert.databinding.FragmentFollowBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowFragment : Fragment() {
    private var _binding: FragmentFollowBinding? = null
    private var _followAdapter: FollowUserAdapter? = null
    private val binding get() = _binding!!
    private val followAdapter get() = _followAdapter
    private val followViewModel: FollowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val index = arguments?.getInt(SECTION_NUMBER, 0)
            val username = arguments?.getString(EXTRA_USERNAME).toString()
            _followAdapter = FollowUserAdapter()

            with(binding.rvFollow) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = followAdapter
            }

            when (index) {
                1 -> {
                    getFollow(username, "followers")
                }

                2 -> {
                    getFollow(username, "following")
                }
            }
        }
    }

    private fun getFollow(username: String, type: String) {
        followViewModel.getFollow(username, type).observe(viewLifecycleOwner) { listFollow ->
            if (listFollow != null) {
                when (listFollow) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.rvFollow.visibility = View.INVISIBLE
                    }

                    is Resource.Success -> {
                        binding.apply {
                            progressBar.visibility = View.GONE
                            rvFollow.visibility = View.VISIBLE
                            noFollow.text = getString(R.string.no_user, type)
                            noFollow.visibility =
                                if (listFollow.data.isNullOrEmpty()) View.VISIBLE else View.GONE
                        }
                        followAdapter?.setData(listFollow.data)
                    }

                    is Resource.Error -> {
                        binding.apply {
                            noFollow.visibility = View.GONE
                            rvFollow.visibility = View.INVISIBLE
                            viewError.root.visibility = View.VISIBLE
                            viewError.tvError.text =
                                listFollow.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val SECTION_NUMBER = "section_number"
        const val EXTRA_USERNAME = "extra_username"
    }
}