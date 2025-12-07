package com.example.gamenest.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.annotation.DrawableRes
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamenest.model.Game
import com.example.gamenest.R
import com.example.gamenest.ui.theme.GameNestTheme

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

@Composable
fun GameCard(game: Game, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable { onClick() }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            SafeImage(
                resId = game.imageRes,
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
fun BottomNavBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.ic_star), contentDescription = "Featured")
                Image(painter = painterResource(id = R.drawable.ic_history), contentDescription = "History", modifier = Modifier.padding(start = 40.dp))
                Image(painter = painterResource(id = R.drawable.ic_profile), contentDescription = "Profile", modifier = Modifier.padding(start = 40.dp))
            }
            Text(text = "Featured · History · Profile", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GameCardPreview() {
    GameNestTheme {
        GameCard(
            game = Game(
                id = "g1",
                name = "Fortnite",
                description = "Battle royale com construção e ação.",
                imageRes = R.drawable.fortnite,
                items = emptyList()
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavBarPreview() {
    GameNestTheme { BottomNavBar() }
}
