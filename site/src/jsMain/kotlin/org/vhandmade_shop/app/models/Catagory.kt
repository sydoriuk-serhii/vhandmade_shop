package org.vhandmade_shop.app.models

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String,
    val name: String,
    val slug: String // Для використання в URL, напр. /catalog/my-category
)