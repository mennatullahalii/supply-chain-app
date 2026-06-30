package com.example.order_service.order

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "orders")
data class PurchaseOrder(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val sku: String,
    val quantity: Int,
    val status: String = "PENDING",
    val totalPrice: BigDecimal
)