# MobileTech Utility App

Project Overview

"Mindful Minutes" is an Android utility app designed to help users engage in short meditation and mindfulness sessions. Users can select a meditation duration, control a countdown timer, and view randomly generated inspirational quotes.

This project was developed for the "Assessment 1: Utility App" assignment in the CP3406 course.
---

## Features

- Meditation Timer: Choose from meditation durations of 5, 10, or 15 minutes.
- Start and Pause: Start or pause the countdown timer.
- Reset Timer: Reset the countdown to a newly selected duration.
- Completion: Displays a notification message when the countdown reaches zero.
- Settings: Configure the default meditation duration.
- Random Quotes: Fetches and displays inspirational quotes and their authors via the ZenQuotes API.
- Loading and Error Handling: Shows a loading indicator while fetching quotes; displays a user-friendly error message if the request fails.
- Bottom Navigation: Switch between the "Utility" page and the "Settings" page.

## Technologies Used

- Kotlin
- Android Studio
- Jetpack Compose
- Material Design 3
- Android ViewModel
- Kotlin Coroutines and StateFlow
- Repository pattern
- Retrofit (for HTTP requests)
- Kotlinx Serialization (for JSON parsing)
- Koin (for dependency injection)
- JUnit and Mockito (for unit testing)

## External API

This app uses the [ZenQuotes API](https://zenquotes.io/) to retrieve random inspirational quotes.

API Endpoint: `https://zenquotes.io/api/random`

The API response includes the quote content and the author's name. The app displays these quotes and attributes them to ZenQuotes.

Please refer to the official ZenQuotes website for the latest terms of use and request limits.

## Project Structure

- `MainActivity.kt` – Contains the user interface (UI) for the app’s main navigation, utility pages, settings, and a meditation timer.
- `data/Quote.kt` – Defines the data model for quotes.
- `data/QuotesApi.kt` – Defines the Retrofit API interface.
- `data/QuoteRepository.kt` – Fetches quote data via the API interface.
- `di/NetworkModule.kt` – Configures Retrofit, the repository layer, and Koin dependencies.
- `viewmodel/QuoteViewModel.kt` – Manages quote data, loading states, and error states.
- `src/test/` – Contains unit tests for the quote repository and ViewModel.

### How to Run
1. Clone or download this repository.
2. Open the project in Android Studio.
3. Wait for Gradle to complete synchronization and resolve project dependencies.
4. Launch an Android emulator or connect a compatible Android device.
5. Click the "Run" button in Android Studio to start the app.

An internet connection is required to retrieve quotes from ZenQuotes.
---
## Running Unit Tests

To run local unit tests in Android Studio, follow these steps:

1. Navigate to the `app/src/test` directory.
2. Right-click on a test class.
3. Select **Run**.

Alternatively, run the following command from the project root directory:

```powershell
.\gradlew.bat testDebugUnitTest
```
## Acknowledgments

- Quotes are provided by [ZenQuotes.io](https://zenquotes.io/).
- This project was developed for educational purposes as part of Assessment 1 for the course CP3406.