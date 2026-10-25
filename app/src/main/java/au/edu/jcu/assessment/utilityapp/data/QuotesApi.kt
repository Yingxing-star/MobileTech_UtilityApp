package au.edu.jcu.assessment.utilityapp.data

import retrofit2.http.GET

interface QuotesApi {
    @GET("random")
    suspend fun getRandomQuote(): List<Quote>
}
