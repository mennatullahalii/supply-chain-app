package com.example.order_service.order

import com.example.order_service.event.OrderEventProducer
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = ["*"])
class OrderController(private val orderRepository: OrderRepository,
                      private val orderEventProducer: OrderEventProducer) {

    @PostMapping
    fun placeOrder(@RequestBody request: OrderRequest): PurchaseOrder {

        val price = BigDecimal("10.00").multiply(BigDecimal(request.quantity))

        val newOrder = PurchaseOrder(
            sku = request.sku,
            quantity = request.quantity,
            totalPrice = price
        )
        val savedOrder = orderRepository.save(newOrder)

        orderEventProducer.sendOrderCreatedEvent(
            orderId = savedOrder.id.toString(),
            productId = savedOrder.sku,
            quantity = savedOrder.quantity
        )

        return savedOrder

    }
}

data class OrderRequest(val sku: String, val quantity: Int)