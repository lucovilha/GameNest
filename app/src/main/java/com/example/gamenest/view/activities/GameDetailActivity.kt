package com.example.gamenest.view.activities

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.foundation.shape.CircleShape
import com.example.gamenest.R
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
                        name = "Fortnite",
                        description = "Battle royale com construção e ação.",
                        imageRes = R.drawable.fortnite,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GameDetailScreen(game: Game, onBack: () -> Unit) {
    val selectedItem = remember { mutableStateOf<ShopItem?>(null) }
    val ctx = LocalContext.current

    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Box(modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = painterResource(id = game.imageRes),
                    contentDescription = game.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
                androidx.compose.material3.Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.TopStart)
                        .clickable { onBack() }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back",
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }

            Text(
                text = game.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

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
private fun ItemRow(item: ShopItem, onClick: () -> Unit) {
    androidx.compose.material3.Surface(
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun GameDetailPreview() {
    GameNestTheme {
        GameDetailScreen(
            game = Game(
                id = "g1",
                name = "Fortnite",
                description = "Battle royale com construção e ação.",
                imageRes = R.drawable.fortnite,
                items = listOf(
                    ShopItem("p1", "Space Skin", "A bright galaxy skin.", 12.99, R.drawable.img_item1),
                    ShopItem("p2", "Booster Pack", "Boost speed for 3 rounds.", 9.49, R.drawable.img_item2),
                    ShopItem("p3", "Golden Blaster", "Laser upgrade.", 14.99, R.drawable.img_item3)
                )
            ),
            onBack = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun GameDetailPreviewFc() {
    GameNestTheme {
        GameDetailScreen(
            game = Game(
                id = "g2",
                name = "FC 26",
                description = "Futebol com modos competitivos e clubes.",
                imageRes = R.drawable.fc26,
                items = listOf(
                    ShopItem("p4", "Jersey Classic", "Uniforme clássico.", 10.99, R.drawable.img_item4),
                    ShopItem("p5", "Stadium Pass", "Acesso premium ao estádio.", 8.49, R.drawable.img_item5),
                    ShopItem("p6", "Boots Pro", "Chuteiras profissionais.", 15.99, R.drawable.img_item6)
                )
            ),
            onBack = {}
        )
    }
}
