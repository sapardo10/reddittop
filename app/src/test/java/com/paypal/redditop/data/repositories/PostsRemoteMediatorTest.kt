package com.paypal.redditop.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.*
import com.paypal.redditop.MainCoroutineRule
import com.paypal.redditop.data.datasources.IPostLocalDataSource
import com.paypal.redditop.data.datasources.IPostRemoteDataSource
import com.paypal.redditop.models.BaseResponse
import com.paypal.redditop.models.PostsKeys
import com.paypal.redditop.models.Result
import com.paypal.redditop.models.SimplePost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.rules.TestRule
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class PostsRemoteMediatorTest {

    @ExperimentalCoroutinesApi
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var remoteMediator: PostsRemoteMediator

    /**
     * ------------------------------------------ MOCKS --------------------------------------------
     */

    private val mockPostLocalDataSource = mock(IPostLocalDataSource::class.java)
    private val mockPostRemoteDataSource = mock(IPostRemoteDataSource::class.java)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        remoteMediator = PostsRemoteMediator(
            postLocalDataSource = mockPostLocalDataSource,
            postRemoteDataSource = mockPostRemoteDataSource
        )
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(
            mockPostLocalDataSource,
            mockPostRemoteDataSource
        )
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `load - Load state is refresh and remote response is successful`() {
        runBlocking {
            val state = PagingState(
                pages = listOf<PagingSource.LoadResult.Page<Int, SimplePost>>(),
                anchorPosition = null,
                config = PagingConfig(
                    pageSize = 10
                ),
                leadingPlaceholderCount = 2
            )
            `when`(
                mockPostRemoteDataSource.getTopPosts(
                    null,
                    null,
                    state.config.pageSize
                )
            ).thenReturn(
                Result.Success(
                    data = BaseResponse(
                        "a",
                        "b",
                        listOf()
                    )
                )
            )

            val result = remoteMediator.load(
                loadType = LoadType.REFRESH,
                state = state
            )

            verify(mockPostLocalDataSource).clearAll()
            verify(mockPostRemoteDataSource).getTopPosts(
                null,
                null,
                state.config.pageSize
            )
            verify(mockPostLocalDataSource).insertPosts(
                afterKey = "a",
                beforeKey = "b",
                cleanBeforeSaving = false,
                posts = listOf()
            )

            assertTrue(result is RemoteMediator.MediatorResult.Success)
        }
    }

    @Test
    fun `load - Load state is refresh and remote response is failure`() {
        runBlocking {
            val state = PagingState(
                pages = listOf<PagingSource.LoadResult.Page<Int, SimplePost>>(),
                anchorPosition = null,
                config = PagingConfig(
                    pageSize = 10
                ),
                leadingPlaceholderCount = 2
            )
            `when`(
                mockPostRemoteDataSource.getTopPosts(
                    null,
                    null,
                    state.config.pageSize
                )
            ).thenReturn(
                Result.Failure(Exception())
            )

            val result = remoteMediator.load(
                loadType = LoadType.REFRESH,
                state = state
            )

            verify(mockPostLocalDataSource).clearAll()
            verify(mockPostRemoteDataSource).getTopPosts(
                null,
                null,
                state.config.pageSize
            )
            assertTrue(result is RemoteMediator.MediatorResult.Error)
        }
    }

    @Test
    fun `load - Load state is prepend`() {
        runBlocking {
            val state = PagingState(
                pages = listOf<PagingSource.LoadResult.Page<Int, SimplePost>>(),
                anchorPosition = null,
                config = PagingConfig(
                    pageSize = 10
                ),
                leadingPlaceholderCount = 2
            )

            val result = remoteMediator.load(
                loadType = LoadType.PREPEND,
                state = state
            )

            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }
    }

    @Test
    fun `load - Load state is append and state is empty`() {
        runBlocking {
            val state = PagingState(
                pages = listOf<PagingSource.LoadResult.Page<Int, SimplePost>>(),
                anchorPosition = null,
                config = PagingConfig(
                    pageSize = 10
                ),
                leadingPlaceholderCount = 2
            )

            val result = remoteMediator.load(
                loadType = LoadType.APPEND,
                state = state
            )

            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }
    }

    @Test
    fun `load - Load state is append and remote response is successful`() {
        runBlocking {
            val state = PagingState(
                pages = listOf<PagingSource.LoadResult.Page<Int, SimplePost>>(
                    PagingSource.LoadResult.Page(
                        data = listOf(
                            SimplePost(
                                id = "asdasasd",
                                image = "asdasdas",
                                thumbnail = "asdasdas",
                                title = "asdkasjd",
                                upVotes = 2132,
                                videoUrl = "asdasdas",
                                author = "asdhkajsd",
                                authorUrl = "jivdskfeijfas",
                                subreddit = "klsjdkasjdklas",
                                subredditUrl = "sdjnkjsankdnas",
                                created = 48927894312L,
                                isNsfw = false,
                                isVideo = false,
                                isAds = false,
                                numComments = 423423,
                                totalAwards = 324,
                                url = "asjhkdasd"
                            )
                        ), nextKey = null, prevKey = null
                    )
                ),
                anchorPosition = null,
                config = PagingConfig(
                    pageSize = 10
                ),
                leadingPlaceholderCount = 2
            )
            `when`(mockPostLocalDataSource.getLastKeys()).thenReturn(
                PostsKeys(
                    1, "a", "b"
                )
            )
            `when`(
                mockPostRemoteDataSource.getTopPosts(
                    "a",
                    "b",
                    state.config.pageSize
                )
            ).thenReturn(
                Result.Success(
                    data = BaseResponse(
                        "a",
                        "b",
                        listOf()
                    )
                )
            )

            val result = remoteMediator.load(
                loadType = LoadType.APPEND,
                state = state
            )

            verify(mockPostLocalDataSource).getLastKeys()
            verify(mockPostRemoteDataSource).getTopPosts(
                "a",
                "b",
                state.config.pageSize
            )
            verify(mockPostLocalDataSource).insertPosts(
                afterKey = "a",
                beforeKey = "b",
                cleanBeforeSaving = false,
                posts = listOf()
            )

            assertTrue(result is RemoteMediator.MediatorResult.Success)
        }
    }

    @Test
    fun `load - Load state is append and remote response is failure`() {
        runBlocking {
            val state = PagingState(
                pages = listOf<PagingSource.LoadResult.Page<Int, SimplePost>>(
                    PagingSource.LoadResult.Page(
                        data = listOf(
                            SimplePost(
                                id = "asdasasd",
                                image = "asdasdas",
                                thumbnail = "asdasdas",
                                title = "asdkasjd",
                                upVotes = 2132,
                                videoUrl = "asdasdas",
                                author = "asdhkajsd",
                                authorUrl = "jivdskfeijfas",
                                subreddit = "klsjdkasjdklas",
                                subredditUrl = "sdjnkjsankdnas",
                                created = 48927894312L,
                                isNsfw = false,
                                isVideo = false,
                                isAds = false,
                                numComments = 423423,
                                totalAwards = 324,
                                url = "asjhkdasd"
                            )
                        ), nextKey = null, prevKey = null
                    )
                ),
                anchorPosition = null,
                config = PagingConfig(
                    pageSize = 10
                ),
                leadingPlaceholderCount = 2
            )
            `when`(mockPostLocalDataSource.getLastKeys()).thenReturn(
                PostsKeys(
                    1, "a", "b"
                )
            )
            `when`(
                mockPostRemoteDataSource.getTopPosts(
                    "a",
                    "b",
                    state.config.pageSize
                )
            ).thenReturn(
                Result.Failure(java.lang.Exception())
            )

            val result = remoteMediator.load(
                loadType = LoadType.APPEND,
                state = state
            )

            verify(mockPostLocalDataSource).getLastKeys()
            verify(mockPostRemoteDataSource).getTopPosts(
                "a",
                "b",
                state.config.pageSize
            )

            assertTrue(result is RemoteMediator.MediatorResult.Error)
        }
    }
}