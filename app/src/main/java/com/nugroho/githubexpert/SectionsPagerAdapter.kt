package com.nugroho.githubexpert

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nugroho.githubexpert.follow.FollowFragment


class SectionsPagerAdapter(fragment: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragment, lifecycle) {

    var username: String = ""

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.SECTION_NUMBER, position + 1)
            putString(FollowFragment.EXTRA_USERNAME, username)
        }
        return fragment
    }
}