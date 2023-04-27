package ru.quipy.shop.cart.mongo

import org.springframework.data.mongodb.core.mapping.FieldType
import org.springframework.data.mongodb.core.mapping.MongoId
import java.util.UUID

data class CartMongo(
    @MongoId(value = FieldType.STRING)
    val cartIdStr: String,
    val cartId: UUID,
    val userId: UUID,
)