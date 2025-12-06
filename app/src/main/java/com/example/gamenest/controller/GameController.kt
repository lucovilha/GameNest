package com.example.gamenest.controller

import com.example.gamenest.R
import com.example.gamenest.model.Game
import com.example.gamenest.model.ShopItem

object GameController {
    fun sampleGames(): List<Game> {
        val game1Items = listOf(
            ShopItem("g1i1", "Space Skin", "A bright galaxy skin for your ship.", 12.99, R.drawable.img_item1),
            ShopItem("g1i2", "Booster Pack", "Boost speed for 3 rounds.", 9.49, R.drawable.img_item2),
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
}

