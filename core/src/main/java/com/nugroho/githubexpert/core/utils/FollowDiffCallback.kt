package com.nugroho.githubexpert.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.nugroho.githubexpert.core.domain.model.Follow

class FollowDiffCallback (private val oldList: List<Follow>, private val newList: List<Follow>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}