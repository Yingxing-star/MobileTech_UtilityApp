package au.edu.jcu.assessment.utilityapp.viewmodel

import au.edu.jcu.assessment.utilityapp.data.Quote
import au.edu.jcu.assessment.utilityapp.data.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class QuoteViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: QuoteRepository
    private lateinit var viewModel: QuoteViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(QuoteRepository::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadQuote_success_updatesQuote() = runBlocking {
        val expectedQuote = Quote(
            text = "Be yourself.",
            author = "Unknown"
        )

        `when`(repository.getRandomQuote())
            .thenReturn(expectedQuote)

        viewModel = QuoteViewModel(repository)

        viewModel.loadQuote()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(expectedQuote, viewModel.quote.value)
        assertFalse(viewModel.isLoading.value)
        assertEquals(null, viewModel.errorMessage.value)
    }


    @Test
    fun loadQuote_failure_updatesErrorMessage() = runBlocking {
        // Arrange: make the repository throw an exception
        `when`(repository.getRandomQuote())
            .thenThrow(RuntimeException("Network error"))

        viewModel = QuoteViewModel(repository)

        // Act: load a quote
        viewModel.loadQuote()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: check the error state
        assertEquals(
            "Unable to load a quote. Please try again.",
            viewModel.errorMessage.value
        )
        assertFalse(viewModel.isLoading.value)
        assertEquals(null, viewModel.quote.value)
    }

}
