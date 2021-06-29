package com.paypal.redditop.features

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paypal.redditop.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private var postsAdapter = PostsAdapter()
    private lateinit var binding: ActivityMainBinding

    /**
     * ------------------------------------ LIFECYCLE METHODS --------------------------------------
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding.list) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = postsAdapter
            adapter = postsAdapter.withLoadStateHeaderAndFooter(
                header = PostsLoadingAdapter { postsAdapter.retry() },
                footer = PostsLoadingAdapter { postsAdapter.retry() }
            )
        }
        initializeObservers()

    }

    private fun initializeObservers() {
        lifecycleScope.launch {
            viewModel.getPosts().collectLatest {
                postsAdapter.submitData(it)
            }
        }
    }
}