package au.edu.jcu.assessment.utilityapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.edu.jcu.assessment.utilityapp.data.Quote
import au.edu.jcu.assessment.utilityapp.data.QuoteRepository
import kotlinx.coroutines.launch

class QuoteViewModel(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    var quote: Quote? = null
        private set

    var isLoading: Boolean = false
        private set

    var errorMessage: String? = null
        private set

    fun loadQuote() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                quote = quoteRepository.getRandomQuote()
            } catch (e: Exception) {
                errorMessage = "Unable to load a quote. Please try again."
            }

            isLoading = false
        }
    }
}