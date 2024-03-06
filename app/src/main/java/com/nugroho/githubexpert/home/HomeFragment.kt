package com.nugroho.githubexpert.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nugroho.githubexpert.R
import com.nugroho.githubexpert.core.data.Resource
import com.nugroho.githubexpert.core.ui.ListUserAdapter
import com.nugroho.githubexpert.databinding.FragmentHomeBinding
import com.nugroho.githubexpert.detail.DetailUserFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var _userAdapter: ListUserAdapter? = null
    private val binding get() = _binding!!
    private val userAdapter get() = _userAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            _userAdapter = ListUserAdapter()
            userAdapter?.onItemClick = { selectedUser ->
                val bundle = Bundle()
                val detailUserFragment = DetailUserFragment()
                bundle.putParcelable(DetailUserFragment.EXTRA_USER, selectedUser)
                detailUserFragment.arguments = bundle
                parentFragmentManager.commit {
                    replace(
                        R.id.container,
                        detailUserFragment,
                        DetailUserFragment::class.java.simpleName
                    )
                    addToBackStack(null)
                }
            }

            with(binding.rvUser) {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                adapter = userAdapter
            }

            val searchManager =
                requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
            with(binding.searchView) {
                setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
                setOnQueryTextListener(object : OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let {
                            getSearchUser(it)
                        }
                        hideKeyboard()
                        return true
                    }

                    override fun onQueryTextChange(query: String?): Boolean = false
                })
            }

            binding.btnFavorite.setOnClickListener {
                val uri = Uri.parse("githubexpert://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
    }

    private fun getSearchUser(q: String) {
        homeViewModel.getSearchUser(q).observe(viewLifecycleOwner) { user ->
            if (user != null) {
                when (user) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.viewStarter.root.visibility = View.GONE
                    }

                    is Resource.Success -> {
                        binding.apply {
                            progressBar.visibility = View.GONE
                            rvUser.visibility = View.VISIBLE
                            viewError.root.visibility = View.GONE
                        }
                        userAdapter?.setData(user.data)
                    }

                    is Resource.Error -> {
                        with(binding) {
                            rvUser.visibility = View.GONE
                            progressBar.visibility = View.GONE
                            viewStarter.root.visibility = View.GONE
                            viewError.root.visibility = View.VISIBLE
                            viewError.tvError.text =
                                user.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.rvUser.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _userAdapter = null
    }
}