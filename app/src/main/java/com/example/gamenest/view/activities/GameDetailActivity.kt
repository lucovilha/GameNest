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
import androidx.annotation.DrawableRes
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
        val game = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("game", Game::class.java)
        } else {
            @Suppress("DEPRECATION") intent.getSerializableExtra("game") as? Game
        }
        setContent {
            GameNestTheme {
                GameDetailScreen(
                    game = game ?: Game(
                        id = "demo",
                        name = "FORTNITE",
                        description = "Battle royale com construção e ação.",
                        imageRes = R.drawable.fortnite,
                        items = listOf(
                            ShopItem("d1", "Sun & Scales Pack", "Bundle temático com visual e itens.", 12.99, R.drawable.fortnite),
                            ShopItem("d2", "Cross Comms Pack", "Inclui 600 V-Bucks e acessórios temáticos.", 9.49, R.drawable.fortnitelogo),
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
private fun SafeImage(@DrawableRes resId: Int, contentDescription: String, modifier: Modifier, contentScale: ContentScale) {
    val valid = remember(resId) { isValidDrawable(resId) }
    Image(
        painter = painterResource(id = if (valid) resId else R.drawable.ic_profile),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}

private fun isValidDrawable(@DrawableRes resId: Int): Boolean {
    return resId in setOf(
        R.drawable.fortnite,
        R.drawable.fortnitelogo,
        R.drawable.fc26,
        R.drawable.packfort1,
        R.drawable.packfort2,
        R.drawable.packfort3,
        R.drawable.fifapoints1,
        R.drawable.fifapoints2,
        R.drawable.fifapoints3,
        R.drawable.img_item1,
        R.drawable.img_item2,
        R.drawable.img_item3,
        R.drawable.img_item4,
        R.drawable.img_item5,
        R.drawable.img_item6
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GameDetailScreen(game: Game, onBack: () -> Unit) {
    val selectedItem = remember { mutableStateOf<ShopItem?>(null) }
    val ctx = LocalContext.current

    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Box(modifier = Modifier.padding(16.dp)) {
                SafeImage(
                    resId = game.imageRes,
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
                SafeImage(
                    resId = item.imageRes,
                    contentDescription = item.name,
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.size(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = item.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = item.description, style = MaterialTheme.typography.bodySmall)
                    Text(text = "$${String.format(java.util.Locale.US, "%.2f", item.price)}", style = MaterialTheme.typography.bodyMedium)
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
                            "Acabou de comprar o item ${item.name} por $${String.format(java.util.Locale.US, "%.2f", item.price)}",
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
            SafeImage(
                resId = item.imageRes,
                contentDescription = item.name,
                modifier = Modifier.size(56.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.size(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.name, style = MaterialTheme.typography.titleSmall)
                Text(text = item.description, style = MaterialTheme.typography.bodySmall)
            }
            Text(text = "$${String.format(java.util.Locale.US, "%.2f", item.price)}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun GameDetailPreview() {
    GameNestTheme(darkTheme = false, dynamicColor = false) {
        GameDetailScreen(
            game = Game(
                id = "g1",
                name = "FORTNITE",
                description = "Battle royale com construção e ação.",
                imageRes = R.drawable.fortnite,
                items = listOf(
                    ShopItem("p1", "Sun & Scales Pack", "Bundle temático com visual e itens.", 12.99, R.drawable.packfort1),
                    ShopItem("p2", "Cross Comms Pack", "Inclui 600 V-Bucks e acessórios temáticos.", 9.49, R.drawable.packfort2),
                    ShopItem("p3", "Minty Pack", "picareta de natal.", 14.99, R.drawable.packfort3)
                )
            ),
            onBack = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun GameDetailPreviewFc() {
    GameNestTheme {
        GameDetailScreen(
            game = Game(
                id = "g2",
                name = "FC26",
                description = "Futebol com modos competitivos e clubes.",
                imageRes = R.drawable.fc26,
                items = listOf(
                    ShopItem("p4", "fifa points(1050)", "invista nos fifas points.", 8.49, R.drawable.fifapoints1),
                    ShopItem("p5", "fifa points(2800)", "invista nos fifas points.", 10.99, R.drawable.fifapoints2),
                    ShopItem("p6", "fifa points(5900)", "invista nos fifas points.", 15.99, R.drawable.fifapoints3)
                )
            ),
            onBack = {}
        )
    }
}
