package ru.quipy.shop.cart.mongo

import com.mongodb.lang.Nullable
import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.shop.product.mongo.ProductMongo
import java.util.UUID

interface CartMongoDao : MongoRepository<CartMongo, String> {
    @Nullable
    fun findOneByUserId(userId: UUID): CartMongo
}