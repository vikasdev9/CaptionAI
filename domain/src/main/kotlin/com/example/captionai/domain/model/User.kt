package com.example.captionai.domain.model

data class User(
    val id: String = "",
    val name: String = "",
    val handle: String = "",
    val email: String = "",
    val profileImageUrl: String = "",
    val isPremium: Boolean = false,
    val trialDaysLeft: Int = 0,
    val stats: UserStats = UserStats()
)

data class UserStats(
    val generations: Int = 0,
    val saved: Int = 0,
    val streak: Int = 0
)
