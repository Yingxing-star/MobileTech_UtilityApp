package au.edu.jcu.assessment.utilityapp.di

import au.edu.jcu.assessment.utilityapp.data.QuoteRepository
import au.edu.jcu.assessment.utilityapp.data.QuotesApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType

val networkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
        }
    }

    single<QuotesApi> {
        val json: Json = get()

        Retrofit.Builder()
            .baseUrl("https://zenquotes.io/api/")
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(QuotesApi::class.java)
    }

    single {
        QuoteRepository(get())
    }
}
