package com.paypal.redditop.features.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.paypal.redditop.databinding.ActivityMainBinding
import com.paypal.redditop.features.detail.DetailsActivity
import com.paypal.redditop.models.SimplePost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var postsAdapter: PostsAdapter? = null
    private val viewModel: MainViewModel by viewModels()

    /**
     * ------------------------------------ LIFECYCLE METHODS --------------------------------------
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializePostsList()
        setInteractionsListeners()
        initializeObserverOverPosts()
    }

    /**
     * ------------------------------------- PRIVATE METHODS ---------------------------------------
     */

    /**
     * Method that initializes the [RecyclerView] and the [PostsAdapter] that paint the list of
     * posts on the screen
     */
    private fun initializePostsList() {
        postsAdapter = PostsAdapter(
            this::onItemClicked
        )
        binding.list.apply {
            adapter = postsAdapter
            adapter = postsAdapter?.withLoadStateHeaderAndFooter(
                header = PostsLoadingAdapter {
                    retryFetchData()
                },
                footer = PostsLoadingAdapter {
                    retryFetchData()
                }
            )
            postsAdapter?.addLoadStateListener(onAdapterLoadStateChanged())

            binding.retryButton.setOnClickListener {
                retryFetchData()
            }
        }
    }

    /**
     * Method that act as a callback for the different state changes that the [postsAdapter] can
     * have and react accordingly
     */
    private fun onAdapterLoadStateChanged() = { loadState: CombinedLoadStates ->
        binding.swipeRefresh.isVisible = loadState.mediator?.refresh is LoadState.NotLoading
        binding.progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
        binding.retryButton.isVisible = loadState.mediator?.refresh is LoadState.Error
    }

    /**
     * Method called when the user taps on one of the items of the screen
     * @param simplePost [SimplePost] model that represents the item that was clicked
     * @param view [View] that was clicked on the recycler view
     */
    private fun onItemClicked(simplePost: SimplePost, view: View) {
        val intent = Intent(this, DetailsActivity::class.java)

        val thumbnailTransition = androidx.core.util.Pair(view, "thumbnail")
        val titleTransition = androidx.core.util.Pair(view, "title")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            thumbnailTransition,
            titleTransition
        )
        intent.putExtra(DetailsActivity.POST_TITLE_KEY, simplePost.title)
        intent.putExtra(DetailsActivity.POST_ID_KEY, simplePost.id)
        startActivity(intent, options.toBundle())
    }

    /**
     * Method called when a reason is met to retry the fetch of the data in the adapter
     */
    private fun retryFetchData() {
        postsAdapter?.retry()
    }

    /**
     * Method that sets the listener for the interactions that this screen can have
     */
    private fun setInteractionsListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            postsAdapter?.refresh()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    /**
     * Method that initializes the observer over the changes on the list of posts to update the
     * list on the screen accordingly
     */
    private fun initializeObserverOverPosts() {
        lifecycleScope.launch {
            viewModel.getPosts().collectLatest {
                postsAdapter?.submitData(it)
            }
        }
    }
}