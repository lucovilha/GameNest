package com.example.gamenest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamenest.model.Game
import com.example.gamenest.model.ShopItem
import com.example.gamenest.ui.theme.GameNestTheme

class GameDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val game = intent.getSerializableExtra("game") as? Game
        setContent {
            GameNestTheme {
                GameDetailScreen(
                    game = game ?: Game(
                        id = "demo",
                        name = "Demo Game",
                        description = "Example game description for preview.",
                        imageRes = R.drawable.img_game1,
                        items = listOf(
                            ShopItem("d1", "Demo Item", "Preview only", 9.99, R.drawable.img_item1),
                            ShopItem("d2", "Demo Item 2", "Preview only", 5.49, R.drawable.img_item2),
                            ShopItem("d3", "Demo Item 3", "Preview only", 12.99, R.drawable.img_item3)
                        )
                    ),
                    onBack = { finish() }
                )
            }
        }
    }
}

@Composable
private fun GameDetailScreen(game: Game, onBack: () -> Unit) {
    val selectedItem = remember { mutableStateOf<ShopItem?>(null) }
    val ctx = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(game.name) },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back",
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(24.dp)
                            .clickable { onBack() }
                    )
                },
                actions = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_heart),
                        contentDescription = "Favorite",
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(24.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = { DetailBottomBar() }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = game.imageRes),
                    contentDescription = game.name,
                    modifier = Modifier.size(96.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = game.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Text(
                text = "Purchasable Items",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(game.items) { item ->
                    ItemRow(item = item) { selectedItem.value = item }
                }
            }
        }
    }

    if (selectedItem.value != null) {
        val item = selectedItem.value!!
        ModalBottomSheet(onDismissRequest = { selectedItem.value = null }) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.name,
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.size(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = item.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = item.description, style = MaterialTheme.typography.bodySmall)
                    Text(text = "$${String.format("%.2f", item.price)}", style = MaterialTheme.typography.bodyMedium)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.clickable {
                        Toast.makeText(
                            ctx,
                            "Acabou de comprar o item ${item.name} por $${String.format("%.2f", item.price)}",
                            Toast.LENGTH_SHORT
                        ).show()
                        selectedItem.value = null
                    }
                ) {
                    Text(
                        text = "Buy with 1-click",
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailBottomBar() {
    val ctx = LocalContext.current
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.layout.Column(horizontalAlignment = Alignment.CenterHorizontally) {
            androidx.compose.foundation.layout.Row(
                horizontalArrangement = Arrangement.spacedBy(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.ic_star), contentDescription = "Featured", modifier = Modifier.clickable {
                    ctx.startActivity(android.content.Intent(ctx, MainActivity::class.java))
                })
                Image(painter = painterResource(id = R.drawable.ic_history), contentDescription = "History", modifier = Modifier.clickable {
                    ctx.startActivity(android.content.Intent(ctx, HistoryActivity::class.java))
                })
                Image(painter = painterResource(id = R.drawable.ic_profile), contentDescription = "Profile", modifier = Modifier.clickable {
                    val user = com.example.gamenest.model.User("User Name", "user@email.com")
                    ctx.startActivity(android.content.Intent(ctx, ProfileActivity::class.java).apply { putExtra("user", user) })
                })
            }
            Text(
                text = "Featured · History · Profile",
                style = MaterialTheme.typography.bodySmall,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
private fun ItemRow(item: ShopItem, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                modifier = Modifier.size(56.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.name, style = MaterialTheme.typography.titleSmall)
                Text(text = item.description, style = MaterialTheme.typography.bodySmall)
            }
            Text(text = "$${String.format("%.2f", item.price)}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemRowPreview() {
    GameNestTheme {
        ItemRow(
            item = ShopItem(
                id = "p1",
                name = "Preview Item",
                description = "Item description in brief detail.",
                price = 12.99,
                imageRes = R.drawable.img_item1
            ),
            onClick = {}
        )
    }
}
