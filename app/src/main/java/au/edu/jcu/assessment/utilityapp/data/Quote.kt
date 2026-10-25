package au.edu.jcu.assessment.utilityapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Quote(
    @SerialName("q")
    val text: String,

    @SerialName("a")
    val author: String
)