package com.example.order_service.event

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class OrderEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, OrderCreatedEvent>
) {
    fun sendOrderCreatedEvent(orderId: String, productId: String, quantity: Int) {
        val event = OrderCreatedEvent(orderId, productId, quantity)
        kafkaTemplate.send("order-events", orderId, event)
        println("[KAFKA] Event Published: $event")
    }
}