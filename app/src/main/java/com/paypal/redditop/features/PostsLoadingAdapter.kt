package com.paypal.redditop.features

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paypal.redditop.databinding.LoaderPostItemBinding

class PostsLoadingAdapter(
    private val reload: () -> Unit
) : LoadStateAdapter<PostsLoadingAdapter.PostsLoadingAdapterViewHolder>() {

    inner class PostsLoadingAdapterViewHolder(
        private val binding: LoaderPostItemBinding,
        private val reload: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(state: LoadState) {
            if (state is LoadState.Error) {
                binding.retryButton.setOnClickListener {
                    reload()
                }
            } else {
                binding.retryButton.setOnClickListener(null)
            }
            binding.progressBar.isVisible = state is LoadState.Loading
            binding.errorText.isVisible = state is LoadState.Error
            binding.retryButton.isVisible = state is LoadState.Error
            binding.errorText.text = (state as? LoadState.Error)?.error?.localizedMessage ?: ""
        }
    }

    override fun onBindViewHolder(holder: PostsLoadingAdapterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PostsLoadingAdapterViewHolder {
        val itemBinding =
            LoaderPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostsLoadingAdapterViewHolder(itemBinding, reload)
    }
}