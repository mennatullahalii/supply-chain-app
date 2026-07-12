package com.example.inventory_service.event

import com.example.inventory_service.domain.InventoryRecord
import com.example.inventory_service.domain.InventoryRepository
import com.example.inventory_service.domain.ProductStockRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class OrderEventListener(
    private val inventoryRepository: InventoryRepository,
    private val productStockRepository: ProductStockRepository,
    private val kafkaTemplate: KafkaTemplate<String, Any> // 1. Inject the Kafka sender
) {

    @KafkaListener(topics = ["order-events"], groupId = "inventory-group")
    fun handleOrderCreatedEvent(event: OrderCreatedEvent) {
        val stockOpt = productStockRepository.findById(event.productId)
        
        var isAllocated = false
        var failureReason: String? = null

        // 2. Check the warehouse
        if (stockOpt.isPresent) {
            val stock = stockOpt.get()
            if (stock.availableQuantity >= event.quantity) {
                stock.availableQuantity -= event.quantity
                productStockRepository.save(stock)
                isAllocated = true
            } else {
                failureReason = "Insufficient stock available"
            }
        } else {
            failureReason = "Product not found in warehouse"
        }

        // 3. Save local record to MongoDB
        inventoryRepository.save(InventoryRecord(
            orderId = event.orderId,
            productId = event.productId,
            quantity = event.quantity,
            status = if (isAllocated) "ALLOCATED" else "OUT_OF_STOCK"
        ))

        // 4. Create the response contract
        val allocationEvent = InventoryAllocatedEvent(
            orderId = event.orderId,
            productId = event.productId,
            quantity = event.quantity,
            status = if (isAllocated) "SUCCESS" else "FAILED",
            reason = failureReason
        )
        
        // 5. Broadcast it back to Kafka on a new topic!
        kafkaTemplate.send("inventory-responses", allocationEvent)
        println("[KAFKA] Broadcasted inventory response for Order ${event.orderId}: ${allocationEvent.status}")
    }
}