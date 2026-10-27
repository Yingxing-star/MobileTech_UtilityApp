
package au.edu.jcu.assessment.utilityapp.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class QuoteRepositoryTest {

    @Test
    fun getRandomQuote_returnsQuote() = runBlocking {
        // Arrange: create a fake quote
        val expectedQuote = Quote(
            text = "Be yourself.",
            author = "Unknown"
        )

        // Create a mock API
        val quotesApi = mock(QuotesApi::class.java)

        // Set the API response
        `when`(quotesApi.getRandomQuote())
            .thenReturn(listOf(expectedQuote))

        // Create the repository
        val repository = QuoteRepository(quotesApi)

        // Act: get a quote
        val actualQuote = repository.getRandomQuote()

        // Assert: check the result
        assertEquals(expectedQuote, actualQuote)
    }
}
