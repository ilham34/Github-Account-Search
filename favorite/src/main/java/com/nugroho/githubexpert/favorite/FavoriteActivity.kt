package com.nugroho.githubexpert.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nugroho.githubexpert.MainActivity
import com.nugroho.githubexpert.core.ui.ListUserAdapter
import com.nugroho.githubexpert.di.FavoriteModuleDependencies
import com.nugroho.githubexpert.favorite.databinding.ActivityFavoriteBinding
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelFactory

    private var _userAdapter: ListUserAdapter? = null
    private var _binding: ActivityFavoriteBinding? = null
    private val userAdapter get() = _userAdapter
    private val binding get() = _binding
    private val favViewModel: FavoriteViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        _userAdapter = ListUserAdapter()
        userAdapter?.onItemClick = { selectedUser ->
            val intent = Intent(this@FavoriteActivity, MainActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_TARGET, "detail")
            intent.putExtra(MainActivity.EXTRA_SELECTED_USER, selectedUser)
            startActivity(intent)
        }

        favViewModel.favoriteUser.observe(this) { users ->
//            userAdapter?.setData(users) // list tidak muncul
            userAdapter?.setFavoriteData(users)
            binding?.tvNoUser?.visibility = if (users.isEmpty()) View.VISIBLE else View.GONE
        }

        binding?.rvUserFavorite.apply {
            this?.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            this?.setHasFixedSize(true)
            this?.adapter = userAdapter
        }
        binding?.btnBack?.setOnClickListener {
            this@FavoriteActivity.onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _userAdapter = null
    }
}