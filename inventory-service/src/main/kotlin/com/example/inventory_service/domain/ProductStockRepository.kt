package com.example.inventory_service.domain

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductStockRepository : MongoRepository<ProductStock, String>