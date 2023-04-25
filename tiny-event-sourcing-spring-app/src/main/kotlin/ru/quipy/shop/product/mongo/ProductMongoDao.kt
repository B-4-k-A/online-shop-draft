package ru.quipy.shop.product.mongo

import com.mongodb.lang.Nullable
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductMongoDao : MongoRepository<ProductMongo, String> {
    @Nullable
    fun findOneByAggregateId(id: String): ProductMongo
}