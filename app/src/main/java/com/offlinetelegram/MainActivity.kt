package com.offlinetelegram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.offlinetelegram.ui.theme.OfflineTelegramTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OfflineTelegramTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OfflineTelegramApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfflineTelegramApp() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Chats", "Groups", "Channels", "Walkie")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "OfflineTelegram",
                        modifier = Modifier.semantics {
                            contentDescription = "OfflineTelegram main screen"
                        }
                    )
                },
                actions = {
                    IconButton(
                        onClick = { /* TODO: settings */ },
                        modifier = Modifier.semantics {
                            contentDescription = "Open settings"
                        }
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            Icon(
                                when (index) {
                                    0 -> Icons.Default.Chat
                                    1 -> Icons.Default.Group
                                    2 -> Icons.Default.Campaign
                                    else -> Icons.Default.Mic
                                },
                                contentDescription = null
                            )
                        },
                        label = { Text(title) },
                        modifier = Modifier.semantics {
                            contentDescription = "$title tab"
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            if (selectedTab == 3) {
                // Walkie-talkie big PTT button
                FloatingActionButton(
                    onClick = { /* TODO: start PTT */ },
                    modifier = Modifier
                        .size(96.dp)
                        .semantics {
                            contentDescription = "Hold to talk, push to talk button"
                        }
                ) {
                    Icon(
                        Icons.Default.Mic,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                }
            } else {
                FloatingActionButton(
                    onClick = { /* TODO: new chat */ },
                    modifier = Modifier.semantics {
                        contentDescription = "Create new chat, group or channel"
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        }
    ) { padding ->
        when (selectedTab) {
            0 -> ChatListScreen(Modifier.padding(padding))
            1 -> PlaceholderScreen("Groups", "Create or join encrypted groups", Modifier.padding(padding))
            2 -> PlaceholderScreen("Channels", "Public channels with IDs", Modifier.padding(padding))
            3 -> WalkieTalkieScreen(Modifier.padding(padding))
        }
    }
}

@Composable
fun ChatListScreen(modifier: Modifier = Modifier) {
    val sampleChats = listOf(
        "Ali (encrypted)",
        "Family Group",
        "@public_channel",
        "Sara"
    )
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sampleChats) { chat ->
            Card(
                onClick = { /* open chat */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = "Chat with $chat. Double tap to open."
                    }
            ) {
                ListItem(
                    headlineContent = { Text(chat) },
                    supportingContent = { Text("Last message encrypted • just now") },
                    leadingContent = {
                        Icon(Icons.Default.Person, contentDescription = null)
                    }
                )
            }
        }
    }
}

@Composable
fun WalkieTalkieScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Walkie-Talkie Mode",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.semantics {
                contentDescription = "Walkie talkie mode. Hold the big microphone button to speak."
            }
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "Status: Scanning for nearby devices...",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(32.dp))
        Text(
            "All voice is end-to-end encrypted with AES-256-GCM",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun PlaceholderScreen(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(8.dp))
            Text(subtitle, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
