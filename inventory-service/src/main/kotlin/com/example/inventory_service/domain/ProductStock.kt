package com.example.inventory_service.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "product_stock")
data class ProductStock(
    @Id val productId: String,
    var availableQuantity: Int
)