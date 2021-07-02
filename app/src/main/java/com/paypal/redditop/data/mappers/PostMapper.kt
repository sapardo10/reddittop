package com.paypal.redditop.data.mappers

import com.paypal.redditop.core.RedditopConstants.EMPTY_STRING
import com.paypal.redditop.core.RedditopConstants.REDDIT_BASE_URL
import com.paypal.redditop.core.RedditopConstants.REDDIT_SUBREDDITS_PATH
import com.paypal.redditop.core.RedditopConstants.REDDIT_USERS_PATH
import com.paypal.redditop.data.network.dtos.PostDto
import com.paypal.redditop.models.SimplePost
import com.paypal.redditop.utils.isValidImageUrl

fun PostDto.toSimplePost(): SimplePost {
    return SimplePost(
        id = content.name,
        image = content.imageUrl ?: EMPTY_STRING,
        thumbnail = if (content.thumbnail.isValidImageUrl()) {
            content.thumbnail
        } else {
            EMPTY_STRING
        },
        title = content.title,
        upVotes = content.ups,
        videoUrl = content.secureMedia?.redditVideo?.fallbackUrl ?: EMPTY_STRING,
        author = content.author,
        authorUrl = "$REDDIT_BASE_URL$REDDIT_USERS_PATH${content.author}",
        subreddit = content.subredditNamePrefixed,
        subredditUrl = "$REDDIT_BASE_URL$REDDIT_SUBREDDITS_PATH${content.subreddit}",
        created = content.createdUtc * 1000L,
        isNsfw = content.over18,
        isVideo = content.isVideo,
        isAds = content.isAds,
        numComments = content.numComments,
        totalAwards = content.totalAwardsReceived,
        url = "$REDDIT_BASE_URL${content.postUrl}"
    )
}