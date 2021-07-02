package com.paypal.redditop.features.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.paypal.redditop.MainCoroutineRule
import com.paypal.redditop.domain.IGetPostUseCase
import com.paypal.redditop.models.SimplePost
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.*


@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    @ExperimentalCoroutinesApi
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailsViewModel

    /**
     * ------------------------------------------ MOCKS --------------------------------------------
     */

    private val mockGetPostUseCase = mock(IGetPostUseCase::class.java)
    private var mockObserverModel: Observer<SimplePost?> =
        mock(Observer::class.java) as Observer<SimplePost?>

    @Before
    fun setup() {
        viewModel = DetailsViewModel(
            getPostUseCase = mockGetPostUseCase
        )
        viewModel.model.observeForever(mockObserverModel)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(mockGetPostUseCase)
    }

    @Test
    fun getSimplePostDetails() {
        runBlocking {
            val id = "asdasda"
            val simplePost = SimplePost(
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
            `when`(mockGetPostUseCase(id)).thenReturn(simplePost)

            viewModel.getSimplePostDetails(id)

            verify(mockGetPostUseCase)(id)
            verify(mockObserverModel).onChanged(simplePost)
        }
    }

}