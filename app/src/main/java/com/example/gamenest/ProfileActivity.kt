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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamenest.model.User
import com.example.gamenest.ui.theme.GameNestTheme

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val user = intent.getSerializableExtra("user") as? User ?: User("User Name", "user@email.com")
        setContent {
            GameNestTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("GameNest") },
                            actions = {
                                Image(painter = painterResource(id = R.drawable.ic_bell), contentDescription = "Notifications", modifier = Modifier.padding(end = 8.dp))
                                Image(painter = painterResource(id = R.drawable.ic_settings), contentDescription = "Settings", modifier = Modifier.padding(end = 8.dp))
                            },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                        )
                    },
                    bottomBar = { ProfileBottomBar(user) }
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(painter = painterResource(id = R.drawable.ic_profile), contentDescription = "avatar", modifier = Modifier.size(64.dp))
                                Text(text = user.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))
                                Text(text = user.email, style = MaterialTheme.typography.bodySmall)
                            }
                        }

                        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Image(painter = painterResource(id = R.drawable.ic_email), contentDescription = "email", modifier = Modifier.size(20.dp))
                                Text(text = "Email Settings", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = 12.dp))
                            }
                            Divider()
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Image(painter = painterResource(id = R.drawable.ic_account), contentDescription = "account", modifier = Modifier.size(20.dp))
                                Text(text = "Account Settings", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = 12.dp))
                            }
                            Divider()
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Image(painter = painterResource(id = R.drawable.ic_logout), contentDescription = "logout", modifier = Modifier.size(20.dp))
                                Text(text = "Logout", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = 12.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileBottomBar(user: User) {
    val ctx = LocalContext.current
    Box(modifier = Modifier.fillMaxWidth().height(64.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            androidx.compose.foundation.layout.Row(horizontalArrangement = Arrangement.spacedBy(40.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.ic_star), contentDescription = "Featured", modifier = Modifier.clickable {
                    ctx.startActivity(android.content.Intent(ctx, MainActivity::class.java))
                })
                Image(painter = painterResource(id = R.drawable.ic_history), contentDescription = "History", modifier = Modifier.clickable {
                    ctx.startActivity(android.content.Intent(ctx, HistoryActivity::class.java))
                })
                Image(painter = painterResource(id = R.drawable.ic_profile), contentDescription = "Profile")
            }
            Text(text = "Featured · History · Profile", style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfilePreview() {
    GameNestTheme {
        Column(modifier = Modifier.padding(PaddingValues())) {}
    }
}
