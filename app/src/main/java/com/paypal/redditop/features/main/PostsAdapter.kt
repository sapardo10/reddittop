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
import java.text.SimpleDateFormat
import java.util.*

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
            with(binding) {
                root.setOnClickListener {
                    onItemClicked(item, binding.root)
                }
                title.text = item.title
                upVotes.text = item.upVotes.toString()
                image.load(binding.root.context, item.thumbnail, cornerRadius = 25)
                image.isVisible = item.thumbnail.isNotBlank()
                overlay.isVisible = item.thumbnail.isNotBlank() && item.isVideo
                playVideoIcon.isVisible = item.thumbnail.isNotBlank() && item.isVideo
                subreddit.text = item.subreddit
                nsfwBadge.isVisible = item.isNsfw
                adBadge.isVisible = item.isAds
                createdAt.text = getDate(milliSeconds = item.created)
            }
        }

        /**
         * Return date in specified format.
         * @param milliSeconds Date in milliseconds
         * @return String representing date in specified format
         */
        private fun getDate(milliSeconds: Long): String? {
            val formatter = SimpleDateFormat("dd MMM - hh:mm")
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = milliSeconds
            return formatter.format(calendar.time)
        }

    }
}