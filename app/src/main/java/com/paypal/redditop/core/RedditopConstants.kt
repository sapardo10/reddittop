package com.paypal.redditop.core

object RedditopConstants {

    const val EMPTY_STRING = ""
    const val REDDIT_BASE_URL = "https://www.reddit.com"
    const val REDDIT_SUBREDDITS_PATH = "/r/"
    const val REDDIT_USERS_PATH = "/u/"

    val validImageExtensions = listOf<String>(
        "jpg",
        "png",
        "jpeg"
    )
}