package com.nugroho.githubexpert.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nugroho.githubexpert.core.databinding.UserItemBinding
import com.nugroho.githubexpert.core.domain.model.Follow
import com.nugroho.githubexpert.core.utils.FollowDiffCallback

class FollowUserAdapter : RecyclerView.Adapter<FollowUserAdapter.ListViewHolder>() {
    private var listData = ArrayList<Follow>()

    fun setData(newListData: List<Follow>?) {
        if (newListData == null) return

        val diffCallback = FollowDiffCallback(listData, newListData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listData.clear()
        listData.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ListViewHolder(private var binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Follow) {
            with(binding) {
                Glide.with(root)
                    .load(data.avatarUrl)
                    .circleCrop()
                    .into(imageView)
                tvUsername.text = data.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listData[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = listData.size
}