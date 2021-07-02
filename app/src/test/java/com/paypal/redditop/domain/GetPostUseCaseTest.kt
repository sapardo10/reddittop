package com.paypal.redditop.domain

import com.paypal.redditop.data.repositories.IPostRepository
import com.paypal.redditop.models.SimplePost
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito.*

class GetPostUseCaseTest {

    private lateinit var useCase: IGetPostUseCase

    /**
     * ------------------------------------------ MOCKS --------------------------------------------
     */

    private val mockPostRepository = mock(IPostRepository::class.java)

    @Before
    fun setUp() {
        useCase = GetPostUseCase(
            postRepository = mockPostRepository
        )
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mockPostRepository)
    }

    @Test
    operator fun invoke() {
        runBlocking {
            val id = "asjdkas"
            val post = SimplePost(
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
            `when`(mockPostRepository.getPost(id)).thenReturn(post)

            val result = useCase(id)

            verify(mockPostRepository).getPost(id)
            assertEquals(post, result)
        }
    }
}