package au.edu.jcu.assessment.utilityapp.data

class QuoteRepository(
    private val quotesApi: QuotesApi
) {
    suspend fun getRandomQuote(): Quote {
        val quotes = quotesApi.getRandomQuote()
        return quotes.first()
    }
}
