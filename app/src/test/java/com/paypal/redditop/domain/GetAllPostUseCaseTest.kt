package com.paypal.redditop.domain

import com.paypal.redditop.data.repositories.IPostRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetAllPostUseCaseTest {

    private lateinit var useCase: IGetAllPostUseCase

    /**
     * ------------------------------------------ MOCKS --------------------------------------------
     */

    private val mockPostRepository = Mockito.mock(IPostRepository::class.java)

    @Before
    fun setUp() {
        useCase = GetAllPostUseCase(
            postRepository = mockPostRepository
        )
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(mockPostRepository)
    }

    @Test
    operator fun invoke() {
        runBlocking {
            Mockito.`when`(mockPostRepository.getAllPosts()).thenReturn(
                flow { }
            )

            useCase()

            Mockito.verify(mockPostRepository).getAllPosts()
        }
    }
}