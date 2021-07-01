package com.paypal.redditop.data.mappers

import com.paypal.redditop.data.network.dtos.PostDto
import com.paypal.redditop.models.SimplePost

fun PostDto.toSimplePost(): SimplePost {
    return SimplePost(
        id = content.name,
        image = content.imageUrl ?: "",
        thumbnail = content.thumbnail,
        title = content.title,
        upVotes = content.ups,
        downVotes = content.downs,
        upVoteRatio = content.upvoteRatio,
        videoUrl = content.secureMedia?.redditVideo?.fallbackUrl ?: "",
        author = content.author,
        authorUrl = "https://www.reddit.com/u/${content.author}",
        category = content.category ?: "",
        subreddit = content.subredditNamePrefixed,
        subredditUrl = "https://www.reddit.com/r/${content.subreddit}",
        created = content.createdUtc,
        isOriginalContent = content.isOriginalContent,
        isNsfw = content.over18,
        isVideo = content.isVideo,
        isAds = content.isAds,
        numComments = content.numComments,
        totalAwards = content.totalAwardsReceived,
        viewCount = content?.viewCount ?: "",
        url = "https://www.reddit.com${content.postUrl}"
    )
}