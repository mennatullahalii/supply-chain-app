package com.example.inventory_service.event

data class InventoryAllocatedEvent(
    val orderId: String,
    val productId: String,
    val quantity: Int,
    val status: String, // Will be "SUCCESS" or "FAILED"
    val reason: String? = null
)