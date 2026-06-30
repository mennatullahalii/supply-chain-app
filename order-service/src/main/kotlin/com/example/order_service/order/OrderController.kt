package com.example.order_service.order

import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = ["*"])
class OrderController(private val orderRepository: OrderRepository) {

    @PostMapping
    fun placeOrder(@RequestBody request: OrderRequest): PurchaseOrder {

        val price = BigDecimal("10.00").multiply(BigDecimal(request.quantity))

        val newOrder = PurchaseOrder(
            sku = request.sku,
            quantity = request.quantity,
            totalPrice = price
        )
        return orderRepository.save(newOrder)
    }
}

data class OrderRequest(val sku: String, val quantity: Int)