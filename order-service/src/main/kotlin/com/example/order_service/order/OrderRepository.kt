package com.example.order_service.order

import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<PurchaseOrder, Long>