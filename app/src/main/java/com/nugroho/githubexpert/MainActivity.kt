package com.nugroho.githubexpert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.commitNow
import com.nugroho.githubexpert.core.domain.model.User
import com.nugroho.githubexpert.databinding.ActivityMainBinding
import com.nugroho.githubexpert.detail.DetailUserFragment
import com.nugroho.githubexpert.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding get() = _binding
    private var _binding : ActivityMainBinding? = null
    private var user: User? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        if (savedInstanceState == null) {
            val fragmentTarget = intent?.getStringExtra(EXTRA_TARGET).toString()
            user = intent?.getParcelableExtra(EXTRA_SELECTED_USER)
            val bundle = Bundle()
            val detailUserFragment = DetailUserFragment()
            val homeFragment = HomeFragment()
            bundle.putParcelable(DetailUserFragment.EXTRA_USER, user)
            detailUserFragment.arguments = bundle

            if (fragmentTarget == "detail") {
                supportFragmentManager.commit {
                    replace(R.id.container, detailUserFragment, DetailUserFragment::class.java.simpleName)
                }
            } else {
                supportFragmentManager.commit {
                    replace(R.id.container, homeFragment, HomeFragment::class.java.simpleName)
                }
            }
        } else {
            user = savedInstanceState.getParcelable("data")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the data to be restored later
        outState.putParcelable("data", user)
    }

    companion object {
        const val EXTRA_TARGET = "extra_target"
        const val EXTRA_SELECTED_USER = "extra_selected_user"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        user = null
    }
}