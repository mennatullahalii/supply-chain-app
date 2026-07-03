package com.example.order_service.event

data class OrderCreatedEvent(
    val orderId: String,
    val productId: String,
    val quantity: Int,
    val status: String = "CREATED"
)