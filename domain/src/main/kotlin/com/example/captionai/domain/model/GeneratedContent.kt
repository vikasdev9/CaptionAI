package com.example.captionai.domain.model

data class GeneratedContent(
    val id: Int = 0,
    val type: ContentType,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

enum class ContentType {
    CAPTION, HASHTAG, BIO, REEL_IDEA
}
