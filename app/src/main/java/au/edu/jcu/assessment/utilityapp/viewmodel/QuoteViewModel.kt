package au.edu.jcu.assessment.utilityapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.edu.jcu.assessment.utilityapp.data.Quote
import au.edu.jcu.assessment.utilityapp.data.QuoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuoteViewModel(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _quote = MutableStateFlow<Quote?>(null)
    val quote: StateFlow<Quote?> = _quote

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadQuote() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                _quote.value = quoteRepository.getRandomQuote()
            } catch (e: Exception) {
                _errorMessage.value =
                    "Error: ${e.javaClass.simpleName}: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
