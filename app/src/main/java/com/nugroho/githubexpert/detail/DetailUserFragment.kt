package com.nugroho.githubexpert.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nugroho.githubexpert.R
import com.nugroho.githubexpert.SectionsPagerAdapter
import com.nugroho.githubexpert.core.data.Resource
import com.nugroho.githubexpert.core.domain.model.Detail
import com.nugroho.githubexpert.core.domain.model.User
import com.nugroho.githubexpert.databinding.FragmentDetailUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailUserFragment : Fragment() {

    private var _binding: FragmentDetailUserBinding? = null
    private var _sectionPagerAdapter: SectionsPagerAdapter? = null
    private val binding get() = _binding!!
    private val sectionPagerAdapter get() = _sectionPagerAdapter
    private val detailViewModel: DetailUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            setData()

            binding.btnBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            binding.btnShare.setOnClickListener {
                val share = Intent(Intent.ACTION_SEND)
                share.type = "text/plain"
                val url = "https://github.com/${binding.tvUsername.text}"
                share.putExtra(Intent.EXTRA_TEXT, getString(R.string.value_share, url))
                startActivity(Intent.createChooser(share, getString(R.string.sharew)))
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun setData() {
        val dataUser = arguments?.getParcelable<User>(EXTRA_USER)

        dataUser?.let {
            getDetail(it.login)
            toggleFavorite(it, it.isFavorite)
        }

        _sectionPagerAdapter = SectionsPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        sectionPagerAdapter?.username = dataUser?.login.toString()


        binding.viewPager.adapter = sectionPagerAdapter
        val tabTitle = resources.getStringArray(R.array.tab_title)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    private fun toggleFavorite(user: User, isFavorite: Boolean) {
        var statusFavorite = isFavorite
        binding.toggleButton.isChecked = statusFavorite
        binding.toggleButton.setOnClickListener {
            statusFavorite = !statusFavorite
            if (statusFavorite) {
                detailViewModel.setFavoriteUser(user, true)
            } else {
                detailViewModel.setFavoriteUser(user, false)
            }
            binding.toggleButton.isChecked = statusFavorite
        }
    }

    private fun setDetail(users: Detail?) {
        binding.apply {
            Glide.with(requireContext())
                .load(users?.avatarUrl)
                .circleCrop()
                .into(imgUser)
            tvName.text = users?.name ?: getString(R.string.no_name)
            tvUsername.text = users?.login
            tvUsername2.text = users?.login
            tvFollower.text = users?.followers.toString()
            tvFollowing.text = users?.following.toString()
            tvCompany.text = users?.company ?: getString(R.string.no_company)
            tvLocation.text = users?.location ?: getString(R.string.no_location)
            tvRepo.text = users?.repos.toString()
        }
    }

    private fun getDetail(login: String) {
        detailViewModel.getDetailUser(login).observe(viewLifecycleOwner) { detailUser ->
            if (detailUser != null) {
                when (detailUser) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Log.e("Data Detail", "${detailUser.data}")
                        setDetail(detailUser.data)
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnShare.visibility = View.GONE
                        binding.toggleButton.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text =
                            detailUser.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewPager.adapter = null
        _binding = null
        _sectionPagerAdapter = null
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}