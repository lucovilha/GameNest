package com.example.gamenest.model

import java.io.Serializable

data class ShopItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageRes: Int
) : Serializable

data class Game(
    val id: String,
    val name: String,
    val description: String,
    val imageRes: Int,
    val items: List<ShopItem>
) : Serializable

data class HistoryEntry(
    val gameName: String,
    val imageRes: Int,
    val type: String,
    val timeAgo: String
) : Serializable

data class User(
    val name: String,
    val email: String
) : Serializable

