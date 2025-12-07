package com.example.gamenest.controller

import com.example.gamenest.R
import com.example.gamenest.model.Game
import com.example.gamenest.model.ShopItem

object GameController {
    fun sampleGames(): List<Game> {
        val game1Items = listOf(
            ShopItem("g1i1", "Sun & Scales Pack", "Bundle temático com visual e itens.", 12.99, R.drawable.fortnite),
            ShopItem("g1i2", "Cross Comms Pack", "Inclui 600 V-Bucks e acessórios temáticos.", 9.49, R.drawable.fortnitelogo),
            ShopItem("g1i3", "Golden Blaster", "Laser upgrade with golden trail.", 14.99, R.drawable.img_item3)
        )

        val game2Items = listOf(
            ShopItem("g2i1", "Jungle Hat", "Explorer hat with leaf badge.", 7.99, R.drawable.img_item4),
            ShopItem("g2i2", "Torch", "Bright torch to light hidden caves.", 5.49, R.drawable.img_item5),
            ShopItem("g2i3", "Map Fragment", "Reveals secret area on the map.", 11.99, R.drawable.img_item6)
        )

        return listOf(
            Game(
                id = "g1",
                name = "FORTNITE",
                description = "Battle royale com construção e ação.",
                imageRes = R.drawable.fortnite,
                items = game1Items
            ),
            Game(
                id = "g2",
                name = "FC26",
                description = "Futebol com modos competitivos e clubes.",
                imageRes = R.drawable.fc26,
                items = game2Items
            )
        )
    }
}

