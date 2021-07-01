package com.paypal.redditop.data.datasources

import com.paypal.redditop.data.network.RedditApi
import com.paypal.redditop.data.network.dtos.BaseResponseDto
import com.paypal.redditop.data.network.dtos.DataDto
import com.paypal.redditop.models.Result
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.mockito.Mockito
import org.mockito.Mockito.*

class PostRemoteDataSourceTest {

    private lateinit var remoteDataSource: IPostRemoteDataSource

    /**
     * ------------------------------------------ MOCKS --------------------------------------------
     */

    private val mockRedditApi = Mockito.mock(RedditApi::class.java)

    @Before
    fun setUp() {
        remoteDataSource = PostRemoteDataSource(
            redditApi = mockRedditApi
        )
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(
            mockRedditApi
        )
    }

    @Test
    fun getTopPosts() {
        runBlocking {
            `when`(mockRedditApi.getTopPosts("a", "b", 25)).thenReturn(
                BaseResponseDto(
                    data = DataDto(
                        after = "d",
                        before = "e",
                        posts = listOf()
                    ),
                )
            )

            val result = remoteDataSource.getTopPosts(
                after = "a",
                before = "b",
                limit = 25
            )

            assertTrue(result is Result.Success)
            assertEquals("d", (result as Result.Success).data.after)
            assertEquals("e", (result as Result.Success).data.before)
            assertTrue((result as Result.Success).data.data.isEmpty())

            verify(mockRedditApi).getTopPosts("a", "b", 25)
        }
    }
}