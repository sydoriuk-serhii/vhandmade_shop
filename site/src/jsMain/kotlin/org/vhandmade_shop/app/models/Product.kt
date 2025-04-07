package org.vhandmade_shop.app.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String, // Унікальний ідентифікатор
    val name: String,
    val description: String,
    val price: Double, // Використовуйте BigDecimal для фінансових розрахунків у реальних проектах
    val imageUrls: List<String>, // Список URL зображень
    val category: String, // Або data class Category
    val sellerId: String? = null // ID продавця, якщо це маркетплейс
    // Можна додати інші поля: рейтинг, кількість на складі, варіанти (розмір, колір) тощо.
)