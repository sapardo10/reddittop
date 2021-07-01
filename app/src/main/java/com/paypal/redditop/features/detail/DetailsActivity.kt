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
import com.paypal.redditop.core.RedditopConstants.MEDIA_TYPE_TEXT_PLAIN
import com.paypal.redditop.databinding.ActivityDetailsBinding
import com.paypal.redditop.models.SimplePost
import com.paypal.redditop.utils.load
import com.paypal.redditop.utils.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private var currentWindow = 0
    private var playbackPosition = 0L
    var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    var videoUrl: String = ""
    private val viewModel: DetailsViewModel by viewModels()

    /**
     * ------------------------------------ LIFECYCLE METHODS --------------------------------------
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setupFabButton()
        showBasicValuesOnUI()
        initializeObservers()
        loadScreenData()
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

    /**
     * ------------------------------------- PRIVATE METHODS ---------------------------------------
     */

    /**
     * Method that given the posts it configures the media related to it. It decides whether to
     * paint an image or a video
     * @param post [SimplePost] to be used to paint the screen
     */
    private fun configureMedia(post: SimplePost) {
        binding.content.video.isVisible = post.isVideo
        if (post.isVideo) {
            setupVideo(post)
        } else {
            setupImage(post)
        }
    }

    /**
     * Method that given the [SimplePost] will enable the Fab button and set the click listener on
     * it with the respective share action
     * @param post [SimplePost] to be used to paint the screen
     */
    private fun enableFabButton(post: SimplePost) {
        binding.fab.isEnabled = true
        binding.fab.setOnSingleClickListener {
            val sendIntent: Intent = Intent().apply {
                this.action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "${post.title} \n${post.url}"
                )
                type = MEDIA_TYPE_TEXT_PLAIN
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    /**
     * Method that initializes the observers on the data exposed by the view model
     */
    private fun initializeObservers() {
        viewModel.model.observe(this, {
            it?.let { post ->
                setToolbarTitle(post)
                configureMedia(post)
                setupTextDetails(post)
                setClickListeners(post)
                enableFabButton(post)
            }
        })
    }

    /**
     * Method called when a new player wants to be initialized so the application can display a
     * video inside this screen
     */
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

    /**
     * Method that gets the id of the post to be shown and calls the view model to get all the
     * details needed to paint the entire screen
     */
    private fun loadScreenData() {
        val postId = intent.getStringExtra(POST_ID_KEY)
        if (postId != null) {
            viewModel.getSimplePostDetails(postId)
        } else {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Method called when the lifecycle of the activity doesn't allow to use the player of the video
     * anymore
     */
    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player?.release()
            player = null
        }
    }

    /**
     * Method that sets all the texts on the view based on the post passed as parameter
     * @param post [SimplePost] to be used to paint the screen
     */
    private fun setupTextDetails(post: SimplePost) {
        with(binding.content) {
            author.text = post.author
            upVotesText.text = post.upVotes.toString()
            subreddit.text = post.subreddit
            commentsText.text = getString(R.string.details_x_comments, post.numComments.toString())
            trophyText.text = getString(R.string.details_x_awards, post.totalAwards.toString())
        }
    }

    /**
     * Method that uses the parameters passed by bundle to make a first screen render with basic
     * values
     */
    private fun showBasicValuesOnUI() {
        intent.getStringExtra(POST_TITLE_KEY)?.let { binding.content.title.text = it }
    }

    /**
     * Method that sets the click listeners on the screen based on the info of the post pased as
     * parameter
     * @param post [SimplePost] to be used to paint the screen
     */
    private fun setClickListeners(post: SimplePost) {
        binding.content.subreddit.setOnSingleClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(post.subredditUrl))
            startActivity(browserIntent)
        }
        binding.content.author.setOnSingleClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(post.authorUrl))
            startActivity(browserIntent)
        }
        binding.content.seeOnRedditButton.setOnSingleClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(post.url))
            startActivity(browserIntent)
        }
    }

    /**
     * Method that sets the toolbar title after the post passed as parameter has been loaded
     * @param post [SimplePost] to be used to paint the screen
     */
    private fun setToolbarTitle(post: SimplePost) {
        supportActionBar?.title = post.subreddit
    }

    /**
     * Method that initializes the fab icon. It disables it so it can only be used when the post
     * details have been loaded
     */
    private fun setupFabButton() {
        binding.fab.isEnabled = false
    }

    /**
     * Method that displays the image of the post, if there isn't any it displays a reddit icon
     * instead
     * @param post [SimplePost] to be used to paint the screen
     */
    private fun setupImage(post: SimplePost) {
        if (post.thumbnail.isBlank()) {
            binding.content.image.setImageResource(R.drawable.ic_reddit_primary)
        } else {
            binding.content.image.load(
                this,
                post.image,
                post.thumbnail,
                fitCenter = true
            )
        }
    }

    /**
     * Method that configures the toolbar to show the back button and sets the toolbar as the
     * support action bar for the activity
     */
    private fun setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Method that displays the video of the post and configures it so it can be shown as needed
     * @param post [SimplePost] to be used to paint the screen
     */
    private fun setupVideo(post: SimplePost) {
        binding.content.image.setBackgroundResource(R.color.black)
        binding.content.image.setImageDrawable(null)
        player = SimpleExoPlayer.Builder(this).build()
        binding.content.video.player = player
        player?.playWhenReady = true
        player?.playbackState
        videoUrl = post.videoUrl
        val mediaItem: MediaItem = MediaItem.fromUri(post.videoUrl)
        player?.setMediaItem(mediaItem)
        player?.prepare()
    }

    /**
     * ------------------------------------ COMPANION OBJECT ---------------------------------------
     */

    companion object {
        const val POST_TITLE_KEY = "title"
        const val POST_ID_KEY = "id"
    }
}