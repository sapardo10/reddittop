# Reddittop

## Introduction

This repository contains the source code for the Redditop application as a code challenge for Venmo/Taller. The main purpose of the application is to list top reddit posts in a paginated fashion and to see the details of them. It also allows you to go directly to reddit app and see the specific post, subreddit or author's page.

## Arquitecture

The current proyect followed a clean architecture approach with MVVM pattern on the presentation layer, use cases for the domain layer and a repository pattern for the data layer. This decision allows that each feature is independent from each other, so if you need to reuse some logic from one feature into another is as simple as inyect the use case with the said logic into the view model from the other feature. Also, following the repository pattern on the data layer it allows us to always relay on the same data and avoid discrepancies within the application. Additionally, the separation of concerns makes it really easy to introduce changes on each of the layers as long as they maintain the contracts, so you can change the way the posts are fetched without changing the view layer or you can change the way you get some post and perform some additional validation on the use case.
As it can be seen on the first commits, the idea was to leave the proyect as a multimodule application, but the use of the android library Paging3, made it necessary for the repository to know some implementation details related to pagination, so the final proyect shares dependencies and its located on a single module: app.

![Clean arquitecture diagram](https://devexperto.com/wp-content/uploads/2018/10/clean-architecture-graph.png)

## Features implemented
* Required features:
  * List Top Reddits with thumbnails.
  * Pull to refresh Reddits.
  * Load more Reddits when reaching the end of the list.
* Additional features:
  * Support for different device resolutions and orientation. This through constraint layout and on the detail screen a different layout on landscape.
  * Display Reddit details for all reddits including the ones without image, with images or with videos.
    * Showing images with cache loading and thumbnail placeholder so the experience is smoother.
    * Displaying a video with controls.
  * Offline mode.
  * Transitions between list and details.
  * Dark mode
  * Link to several reddit pages
  * Share post to contacts
* Technical features:
  * Dependency injection
  * Multilayered arquitecture
  * Unit testing
  * Theme centralization (no hardcoded colors where not needed)
  

## Libraries

The proyect used several libraries, this section will name them and explain the reason why it was choosen and where it was implemented:
* Material related libraries
  * It allows easier implementation of a card from the material design system. It was used on the main list of the application
* Room
  * It allows easier implementation of a local SQLite database. It was used to allow the user to use the application even without internet connection.
* Hilt
  * It allows easier implementation of dagger and makes it really easy to inject dependencies and make dependency inversion while depending only on contracts (interfaces) and making it easy to change the implementations below without changin the classes that depend on it.
* core-ktx and other ktx related libraries
  * It adds a nice, big toolset that makes it more clear and less error prone different implementations on the android level.
* Retrofit
  * It allows an easy implementation to consume third party apis because you only need to build the client and declare an interface to consume an API. It was used to get the top posts from Reddit API.
* Paging3
  * It gives a wide variety of tools and classes that allows the implementation of a paginated list really easy. It was used to create a reactive adapter on the view and to implement the PostMediator that is in charge of all the logic related to pagination and caching locally.
* Glide
  * It saves a lot of time to show different images and resources on the application. It also adds a wide variety of operations that allows caching and UI changed on the view on a more perfomant way.
* Swipe refresh layout
  * Library that gives a view which allows the user to perform the pull-to-refresh gesture. It was used so the user can refresh the list of posts in the application.
* Exoplayer
  * Library that makes it easier and more perfomant reproducing media in android applications. It was used on the details page so the user can see the videos from the post which have a video in it.
* Mockito
  * Gives straight foward implementations to make mocks based on interfaces and perform unit test on classes that depend on others.
* Test coroutines
  * Library that allows the test task to run on a single thread even if its specified otherwise on the code. This creates a much more easier way to test suspend functions without having to worry about asynchronism.
* JUnit
  * Library that allows unit testing and makes it easir to setup and discard resources before and after a test is run.
