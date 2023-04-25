package ru.quipy.shop.product.dto

import java.beans.ConstructorProperties
import java.util.UUID

data class ProductDto
@ConstructorProperties("id", "name", "price", "count")
constructor(val id: UUID, val name: String, val price: Long, val count: Long)