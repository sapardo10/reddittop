package com.paypal.redditop.data.mappers

import com.paypal.redditop.data.network.dtos.PostDto
import com.paypal.redditop.models.SimplePost

fun PostDto.toSimplePost(): SimplePost {
    return SimplePost(
        id = content.name,
        thumbnail = content.thumbnail,
        title = content.title,
        upVotes = content.ups,
        downVotes = content.downs,
        upVoteRatio = content.upvoteRatio
    )
}