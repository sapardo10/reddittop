package com.paypal.redditop.features.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paypal.redditop.core.RedditopConstants.EMPTY_STRING
import com.paypal.redditop.databinding.LoaderPostItemBinding

class PostsLoadingAdapter(
    private val reload: () -> Unit
) : LoadStateAdapter<PostsLoadingAdapter.PostsLoadingAdapterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PostsLoadingAdapterViewHolder {
        val itemBinding =
            LoaderPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsLoadingAdapterViewHolder(itemBinding, reload)
    }

    override fun onBindViewHolder(holder: PostsLoadingAdapterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class PostsLoadingAdapterViewHolder(
        private val binding: LoaderPostItemBinding,
        private val reload: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(state: LoadState) {
            with(binding) {
                if (state is LoadState.Error) {
                    retryButton.setOnClickListener {
                        reload()
                    }
                } else {
                    retryButton.setOnClickListener(null)
                }
                progressBar.isVisible = state is LoadState.Loading
                errorText.isVisible = state is LoadState.Error
                retryButton.isVisible = state is LoadState.Error
                errorText.text =
                    (state as? LoadState.Error)?.error?.localizedMessage ?: EMPTY_STRING
            }
        }
    }
}