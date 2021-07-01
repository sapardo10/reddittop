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
            binding.root.setOnClickListener {
                onItemClicked(item, binding.root)
            }
            binding.title.text = item.title
            binding.upVotes.text = item.upVotes.toString()
            binding.image.load(binding.root.context, item.thumbnail, cornerRadius = 25)
            binding.image.isVisible = item.thumbnail.isNotBlank()
            binding.overlay.isVisible = item.thumbnail.isNotBlank() && item.isVideo
            binding.playVideoIcon.isVisible = item.thumbnail.isNotBlank() && item.isVideo
            binding.subreddit.text = item.subreddit
            binding.nsfwBadge.isVisible = item.isNsfw
            binding.adBadge.isVisible = item.isAds
            binding.createdAt.text = getDate(milliSeconds = item.created, "dd MMM - hh:mm")
        }

        /**
         * Return date in specified format.
         * @param milliSeconds Date in milliseconds
         * @param dateFormat Date format
         * @return String representing date in specified format
         */
        fun getDate(milliSeconds: Long, dateFormat: String?): String? {
            // Create a DateFormatter object for displaying date in specified format.
            val formatter = SimpleDateFormat(dateFormat)

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = milliSeconds
            return formatter.format(calendar.time)
        }

    }
}