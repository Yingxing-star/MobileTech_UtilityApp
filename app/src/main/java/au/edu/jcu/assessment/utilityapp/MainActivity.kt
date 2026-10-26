package au.edu.jcu.assessment.utilityapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.edu.jcu.assessment.utilityapp.ui.theme.MobileTech_UtilityAppTheme
import au.edu.jcu.assessment.utilityapp.di.networkModule
import au.edu.jcu.assessment.utilityapp.viewmodel.QuoteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    private val quoteViewModel: QuoteViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            modules(networkModule)
        }
        enableEdgeToEdge()
        setContent {
            MobileTech_UtilityAppTheme {
                UtilityApp(quoteViewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UtilityAppPreview() {
    MobileTech_UtilityAppTheme {
        UtilityAppPreviewContent()
    }
}

@Composable
fun UtilityAppPreviewContent() {
    Text("Mindful Minutes")
}

@Composable
fun UtilityApp(quoteViewModel: QuoteViewModel) {
    var selectedTab by remember { mutableStateOf("Utility") }
    var defaultMinutes by remember { mutableStateOf(5) }
    var selectedMinutes by remember { mutableStateOf(5) }
    var remainingSeconds by remember { mutableStateOf(300) }
    var isStarted by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Utility") },
                    label = { Text("Utility") },
                    selected = selectedTab == "Utility",
                    onClick = { selectedTab = "Utility" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = selectedTab == "Settings",
                    onClick = { selectedTab = "Settings" }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                "Utility" -> UtilityScreen(
                    defaultMinutes = defaultMinutes,
                    selectedMinutes = selectedMinutes,
                    onSelectedMinutesChange = { selectedMinutes = it },
                    remainingSeconds = remainingSeconds,
                    onRemainingSecondsChange = { remainingSeconds = it },
                    isStarted = isStarted,
                    onIsStartedChange = { isStarted = it },
                    quoteViewModel = quoteViewModel
                )


                "Settings"-> SettingsScreen(defaultMinutes) {
                    defaultMinutes = it
                }
            }
        }
    }
}


@Composable
fun UtilityScreen(
    defaultMinutes: Int,
    selectedMinutes: Int,
    onSelectedMinutesChange: (Int) -> Unit,
    remainingSeconds: Int,
    onRemainingSecondsChange: (Int) -> Unit,
    isStarted: Boolean,
    onIsStartedChange: (Boolean) -> Unit,
    quoteViewModel: QuoteViewModel
) {

    val quote by quoteViewModel.quote.collectAsState()
    val isLoading by quoteViewModel.isLoading.collectAsState()
    val errorMessage by quoteViewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        quoteViewModel.loadQuote()
    }

    LaunchedEffect(isStarted, remainingSeconds) {
        if (isStarted && remainingSeconds > 0) {
            kotlinx.coroutines.delay(1000)
            onRemainingSecondsChange(remainingSeconds - 1)
        } else if (remainingSeconds == 0) {
            onIsStartedChange(false)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Mindful Minutes",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Take a moment for yourself.",
            style = MaterialTheme.typography.bodyLarge
        )
        if (isLoading) {
            Text("Loading quote...")
        }

        if (quote != null) {
            Text(
                text = "\"${quote!!.text}\"",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "- ${quote!!.author}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = {
                quoteViewModel.loadQuote()
            }
        ) {
            Text("New Quote")
        }

        Text(
            text = "Quotes provided by ZenQuotes.io",
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            text = "Choose your meditation time",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = {
                onSelectedMinutesChange(5)
                onRemainingSecondsChange(300)
                onIsStartedChange(false)
            }) {
                Text("5 min")
            }

            Button(onClick = {
                onSelectedMinutesChange(10)
                onRemainingSecondsChange(600)
                onIsStartedChange(false)
            }) {
                Text("10 min")
            }

            Button(onClick = {
                onSelectedMinutesChange(15)
                onRemainingSecondsChange(900)
                onIsStartedChange(false)
            }) {
                Text("15 min")
            }
        }

        Text(
            text = "Selected time: $selectedMinutes minutes",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "${remainingSeconds / 60}:${if (remainingSeconds % 60 < 10) "0" else ""}${remainingSeconds % 60}",
            style = MaterialTheme.typography.displayLarge
        )

        Text(
            text = if (isStarted) {
                "Meditation in progress"
            } else {
                "Ready to begin?"
            },
            style = MaterialTheme.typography.titleMedium
        )

        Button(onClick = {
            if (remainingSeconds == 0) {
                onRemainingSecondsChange(selectedMinutes * 60)
                onIsStartedChange(true)
            } else {
                onIsStartedChange(!isStarted)
            }
        }) {
            Text(
                if (remainingSeconds == 0) {
                    "Start Again"
                } else if (isStarted) {
                    "Pause"
                } else {
                    "Start Meditation"
                }
            )
        }

        Button(onClick = {
            onIsStartedChange(false)
            onRemainingSecondsChange(selectedMinutes * 60)
        }) {
            Text("Reset")
        }
        if (remainingSeconds == 0) {
            Text(
                text = "Meditation complete! Well done.",
                style = MaterialTheme.typography.titleMedium
            )
        }

    }
}



@Composable
fun SettingsScreen(defaultMinutes: Int,
                   onDefaultMinutesChange: (Int) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Default meditation duration",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = {
                onDefaultMinutesChange(5)
            }) {
                Text("5 min")
            }

            Button(onClick = {
                onDefaultMinutesChange(10)
            }) {
                Text("10 min")
            }

            Button(onClick = {
                onDefaultMinutesChange(15)
            }) {
                Text("15 min")
            }
        }

        Text(
            text = "Your default duration is $defaultMinutes minutes.",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Choose a duration that feels comfortable for you.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
