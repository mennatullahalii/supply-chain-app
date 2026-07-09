package com.example.inventory_service.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "inventory_records")
data class InventoryRecord(
    @Id val id: String? = null, // Mongo auto-generates this
    val orderId: String,
    val productId: String,
    val quantity: Int,
    val status: String
)