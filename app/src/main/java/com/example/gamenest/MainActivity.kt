package com.example.gamenest

import android.content.Intent
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamenest.model.Game
import com.example.gamenest.model.ShopItem
import com.example.gamenest.ui.theme.GameNestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameNestTheme {
                val games = remember { sampleGames() }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Name of the company") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        )
                    },
                    bottomBar = {
                        BottomBar()
                    }
                ) { innerPadding ->
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        items(games) { game ->
                            GameCard(game = game) {
                                startActivity(
                                    Intent(this@MainActivity, GameDetailActivity::class.java).apply {
                                        putExtra("game", game)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GameCard(game: Game, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable { onClick() }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = game.imageRes),
                contentDescription = game.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = game.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun BottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            RowIcons()
            Text(
                text = "Featured · History · Profile",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun RowIcons() {
    androidx.compose.foundation.layout.Row(
        horizontalArrangement = Arrangement.spacedBy(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painterResource(id = R.drawable.ic_star), contentDescription = "Featured")
        Image(painterResource(id = R.drawable.ic_history), contentDescription = "History")
        Image(painterResource(id = R.drawable.ic_profile), contentDescription = "Profile")
    }
}

private fun sampleGames(): List<Game> {
    val game1Items = listOf(
        ShopItem(
            id = "g1i1",
            name = "Space Skin",
            description = "A bright galaxy skin for your ship.",
            price = 12.99,
            imageRes = R.drawable.img_item1
        ),
        ShopItem(
            id = "g1i2",
            name = "Booster Pack",
            description = "Boost speed for 3 rounds.",
            price = 9.49,
            imageRes = R.drawable.img_item2
        ),
        ShopItem(
            id = "g1i3",
            name = "Golden Blaster",
            description = "Laser upgrade with golden trail.",
            price = 14.99,
            imageRes = R.drawable.img_item3
        )
    )

    val game2Items = listOf(
        ShopItem(
            id = "g2i1",
            name = "Jungle Hat",
            description = "Explorer hat with leaf badge.",
            price = 7.99,
            imageRes = R.drawable.img_item4
        ),
        ShopItem(
            id = "g2i2",
            name = "Torch",
            description = "Bright torch to light hidden caves.",
            price = 5.49,
            imageRes = R.drawable.img_item5
        ),
        ShopItem(
            id = "g2i3",
            name = "Map Fragment",
            description = "Reveals secret area on the map.",
            price = 11.99,
            imageRes = R.drawable.img_item6
        )
    )

    return listOf(
        Game(
            id = "g1",
            name = "Star Racer",
            description = "Race through nebulas and avoid asteroids.",
            imageRes = R.drawable.img_game1,
            items = game1Items
        ),
        Game(
            id = "g2",
            name = "Jungle Quest",
            description = "Explore ruins and discover ancient secrets.",
            imageRes = R.drawable.img_game2,
            items = game2Items
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun MainPreview() {
    GameNestTheme {
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(sampleGames()) { game ->
                GameCard(game = game, onClick = {})
            }
        }
    }
}
