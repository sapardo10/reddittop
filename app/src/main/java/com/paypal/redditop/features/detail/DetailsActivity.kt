package com.paypal.redditop.features.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util
import com.paypal.redditop.R
import com.paypal.redditop.databinding.ActivityDetailsBinding
import com.paypal.redditop.utils.load
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val viewModel: DetailsViewModel by viewModels()

    var player: SimpleExoPlayer? = null
    var videoUrl: String = ""

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /**
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show()
        }
         **/
        binding.fab.isEnabled = false

        viewModel.model.observe(this, {
            it?.let { post ->

                supportActionBar?.title = post.subreddit

                binding.content.seeOnRedditButton.setOnClickListener {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(post.url))
                    startActivity(browserIntent)
                }
                binding.content.video.isVisible = post.isVideo
                binding.content.video.isVisible = post.isVideo
                if (post.isVideo) {

                    binding.content.image.setBackgroundResource(R.color.black)
                    binding.content.image.setImageDrawable(null)
                    player = SimpleExoPlayer.Builder(this).build()
                    binding.content.video.player = player
                    player?.playWhenReady = true
                    player?.playbackState
                    // Prepare the player with the source.
                    videoUrl = post.videoUrl
                    val mediaItem: MediaItem = MediaItem.fromUri(post.videoUrl)
                    player?.setMediaItem(mediaItem)
                    player?.prepare()
                } else {
                    binding.content.image.load(this, post.image, post.thumbnail, fitCenter = true)
                }
                binding.content.author.text = post.author

                with(binding.content) {
                    upVotesText.text = post.upVotes.toString()
                    subreddit.text = post.subreddit
                    subreddit.setOnClickListener {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(post.subredditUrl))
                        startActivity(browserIntent)
                    }
                    author.setOnClickListener {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(post.authorUrl))
                        startActivity(browserIntent)
                    }
                    commentsText.text = "${post.numComments} \n comments"
                    trophyText.text = "${post.totalAwards} \n awards"
                }
                binding.fab.isEnabled = true
                binding.fab.setOnClickListener {
                    val sendIntent: Intent = Intent().apply {
                        this.action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "${post.title} \n${post.url}"
                        )
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }


            }
        })

        intent.getStringExtra("title")?.let { binding.content.title.text = it }
        intent.getStringExtra("id")?.let { viewModel.getSimplePostDetails(it) }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private var playWhenReady = true;
    private var currentWindow = 0;
    private var playbackPosition = 0L

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player?.release()
            player = null
        }
    }

    private fun initializePlayer() {
        if (player == null) {
            player = SimpleExoPlayer.Builder(this).build()
        }

        binding.content.video.player = player
        player?.playWhenReady = true
        player?.playbackState
        val mediaItem: MediaItem = MediaItem.fromUri(videoUrl)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player!!.playWhenReady = playWhenReady
        player!!.seekTo(currentWindow, playbackPosition)
        player!!.prepare()

    }
}