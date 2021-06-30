package com.paypal.redditop.features.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paypal.redditop.databinding.PostItemBinding
import com.paypal.redditop.models.SimplePost
import com.paypal.redditop.utils.load

class PostsAdapter(
    val onItemClicked: (SimplePost, View) -> Unit
) :
    PagingDataAdapter<SimplePost, PostsAdapter.PostsAdapterViewHolder>(SimplePostDiffUtilCallback()) {

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
            binding.root.setOnClickListener {
                onItemClicked(item, binding.root)
            }
            binding.textView.text = item.title
            binding.upVotes.text = item.upVotes.toString()
            binding.image.load(binding.root.context, item.thumbnail, cornerRadius = 25)
            binding.playVideoIcon.isVisible = item.isVideo
            binding.overlay.isVisible = item.isVideo
        }

    }
}