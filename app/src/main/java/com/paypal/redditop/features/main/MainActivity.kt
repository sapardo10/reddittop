package com.paypal.redditop.features.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.paypal.redditop.databinding.ActivityMainBinding
import com.paypal.redditop.features.detail.DetailsActivity
import com.paypal.redditop.models.SimplePost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private var postsAdapter: PostsAdapter? = null
    private lateinit var binding: ActivityMainBinding

    /**
     * ------------------------------------ LIFECYCLE METHODS --------------------------------------
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        postsAdapter = PostsAdapter(
            this::onItemClicked
        )
        binding.list.apply {
            adapter = postsAdapter
            adapter = postsAdapter?.withLoadStateHeaderAndFooter(
                header = PostsLoadingAdapter {
                    postsAdapter?.retry()
                },
                footer = PostsLoadingAdapter {
                    postsAdapter?.retry()
                }
            )
            postsAdapter?.addLoadStateListener { loadState ->
                // Only show the list if refresh succeeds
                binding.swipeRefresh.isVisible = loadState.mediator?.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh
                binding.progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails
                binding.retryButton.isVisible = loadState.mediator?.refresh is LoadState.Error
            }

            binding.retryButton.setOnClickListener {
                postsAdapter?.retry()
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            postsAdapter?.refresh()
            binding.swipeRefresh.isRefreshing = false
        }
        initializeObservers()

    }

    private fun onItemClicked(simplePost: SimplePost, view: View) {
        val intent = Intent(this, DetailsActivity::class.java)

        val thumbnailTransition = androidx.core.util.Pair(view, "thumbnail")
        val titleTransition = androidx.core.util.Pair(view, "title")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            thumbnailTransition,
            titleTransition
        )
        intent.putExtra("url", simplePost.thumbnail)
        intent.putExtra("title", simplePost.title)
        intent.putExtra("id", simplePost.id)
        startActivity(intent, options.toBundle())
    }

    private fun initializeObservers() {
        lifecycleScope.launch {
            viewModel.getPosts().collectLatest {
                postsAdapter?.submitData(it)
            }
        }
        viewModel.actions.observe(this, {
            when (it) {
                is MainViewModelActions.NavigateToPostDetails -> {
                }
            }
        })
    }
}