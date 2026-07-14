package com.example.order_service.event

import com.example.order_service.domain.OrderRepository
import com.example.shared.event.InventoryAllocatedEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class InventoryResponseListener(private val orderRepository: OrderRepository) {

    @KafkaListener(topics = ["inventory-responses"], groupId = "order-group")
    fun handleInventoryResponse(event: InventoryAllocatedEvent) {
        println("[KAFKA] Received inventory status for order ${event.orderId}: ${event.status}")
        
        // Find the original order in PostgreSQL
        val orderOpt = orderRepository.findById(event.orderId.toLong())
        
        if (orderOpt.isPresent) {
            val order = orderOpt.get()
            
            // Update the state based on warehouse reality
            if (event.status == "SUCCESS") {
                order.status = "CONFIRMED"
            } else {
                order.status = "REJECTED"
                println("Order ${event.orderId} rejected due to: ${event.reason}")
            }
            
            orderRepository.save(order)
            
            // TODO: Trigger a WebSocket broadcast or SSE to update the React frontend live!
        }
    }
}