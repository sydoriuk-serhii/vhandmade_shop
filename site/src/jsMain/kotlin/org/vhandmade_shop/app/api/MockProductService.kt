package org.vhandmade_shop.app.api

import org.vhandmade_shop.app.models.Product

class MockProductService : ProductService {
    private val mockProducts = listOf(
        Product("1", "Лялька-мотанка 'Берегиня'", "Традиційна українська лялька-оберіг", 550.00, listOf("/images/motanka1.jpg", "/images/motanka2.jpg"), "Ляльки"),
        Product("2", "Вишиванка 'Калина'", "Чоловіча сорочка з вишивкою хрестиком", 2100.50, listOf("/images/vyshyvanka1.jpg"), "Одяг"),
        Product("3", "Гончарний горщик", "Глиняний горщик ручної роботи", 320.00, listOf("/images/pottery1.jpg"), "Гончарство", "seller123"),
        Product("4", "Різьблена скринька", "Дерев'яна скринька з карпатським орнаментом", 800.00, listOf("/images/box1.jpg"), "Різьба по дереву")
        // Додайте більше товарів
    )

    override suspend fun getFeaturedProducts(): List<Product> {
        return mockProducts.take(4) 
    }
}