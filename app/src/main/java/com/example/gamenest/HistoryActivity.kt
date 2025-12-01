package com.example.gamenest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamenest.model.HistoryEntry
import com.example.gamenest.model.User
import com.example.gamenest.ui.theme.GameNestTheme

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val entries = intent.getSerializableExtra("history") as? ArrayList<HistoryEntry>
        setContent {
            GameNestTheme {
                val list = remember { entries ?: sampleHistory() }
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Name of the company") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        )
                    },
                    bottomBar = { HistoryBottomBar() }
                ) { innerPadding ->
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        items(list) { entry ->
                            HistoryCard(entry)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryCard(entry: HistoryEntry) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = entry.imageRes), contentDescription = entry.gameName, modifier = Modifier.size(48.dp))
            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(text = entry.gameName, style = MaterialTheme.typography.titleSmall)
                Text(text = entry.type, style = MaterialTheme.typography.bodySmall)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.ic_clock), contentDescription = "time", modifier = Modifier.size(14.dp))
                    Text(text = entry.timeAgo, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(start = 6.dp))
                }
            }
        }
    }
}

@Composable
private fun HistoryBottomBar() {
    val ctx = LocalContext.current
    Box(modifier = Modifier.fillMaxWidth().height(64.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            androidx.compose.foundation.layout.Row(horizontalArrangement = Arrangement.spacedBy(40.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(painterResource(id = R.drawable.ic_star), contentDescription = "Featured", modifier = Modifier.clickable {
                    ctx.startActivity(android.content.Intent(ctx, MainActivity::class.java))
                })
                Image(painterResource(id = R.drawable.ic_history), contentDescription = "History")
                Image(painterResource(id = R.drawable.ic_profile), contentDescription = "Profile", modifier = Modifier.clickable {
                    val user = User("User Name", "user@email.com")
                    ctx.startActivity(android.content.Intent(ctx, ProfileActivity::class.java).apply { putExtra("user", user) })
                })
            }
            Text(text = "Featured · History · Profile", style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
        }
    }
}

private fun sampleHistory(): ArrayList<HistoryEntry> {
    return arrayListOf(
        HistoryEntry("Star Racer", R.drawable.img_game1, "Played", "2 hours ago"),
        HistoryEntry("Star Racer", R.drawable.img_game2, "Purchased Item", "1 day ago"),
        HistoryEntry("Jungle Quest", R.drawable.img_game2, "Played", "3 days ago"),
        HistoryEntry("Jungle Quest", R.drawable.img_game1, "Purchased Item", "1 week ago")
    )
}

@Preview(showBackground = true)
@Composable
private fun HistoryPreview() {
    GameNestTheme {
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(sampleHistory()) { HistoryCard(it) }
        }
    }
}

