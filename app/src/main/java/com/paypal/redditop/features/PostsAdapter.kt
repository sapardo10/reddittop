package com.paypal.redditop.features

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paypal.redditop.databinding.PostItemBinding
import com.paypal.redditop.models.SimplePost

class PostsAdapter : PagingDataAdapter<SimplePost, PostsAdapter.PostsAdapterViewHolder>(SimplePostDiffUtilCallback()) {

    /**
     * ------------------------------------- PUBLIC METHODS ----------------------------------------
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsAdapterViewHolder {
        val itemBinding =
            PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsAdapterViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PostsAdapterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class PostsAdapterViewHolder(private val binding: PostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SimplePost) {
            binding.textView.text = item.title
        }

    }
}