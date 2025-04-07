package org.vhandmade_shop.app.api

import org.vhandmade_shop.app.models.Product

interface ProductService {
    suspend fun getFeaturedProducts(): List<Product>
    // Додамо інші методи пізніше (getProductById, getProductsByCategory, etc.)
}