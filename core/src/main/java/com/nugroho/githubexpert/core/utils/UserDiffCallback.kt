package com.nugroho.githubexpert.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.nugroho.githubexpert.core.domain.model.User

class UserDiffCallback(private val oldList: List<User>, private val newList: List<User>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id && oldList[oldItemPosition].login == newList[newItemPosition].login
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}