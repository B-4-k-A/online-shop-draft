package ru.quipy.shop.product.mongo

import org.springframework.data.mongodb.core.mapping.FieldType
import org.springframework.data.mongodb.core.mapping.MongoId
import java.util.UUID

data class ProductMongo (
    @MongoId(value = FieldType.STRING)
    val aggregateIdStr: String,
    val aggregateId: UUID,
    val name: String,
)