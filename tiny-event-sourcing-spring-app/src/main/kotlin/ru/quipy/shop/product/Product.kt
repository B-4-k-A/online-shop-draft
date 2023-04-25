package ru.quipy.shop.product

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.Setter
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.shop.product.config.ProductAggregate
import ru.quipy.shop.product.events.ProductAddEntitesEvent
import ru.quipy.shop.product.events.ProductChangePrice
import ru.quipy.shop.product.events.ProductCreateEvent

import java.util.UUID

@Getter
@AllArgsConstructor
class Product : AggregateState<UUID, ProductAggregate> {
    private lateinit var id: UUID
    lateinit var name: String
    @Setter
    var price: Long = 0
    var count: Long = 0


    @StateTransitionFunc
    fun createProduct(event: ProductCreateEvent) {
        id = event.productId
        name = event.productName
        price = event.price
    }

    @StateTransitionFunc
    fun changePrice(event: ProductChangePrice) {
        price = event.price
    }

    @StateTransitionFunc
    fun addEntities(event: ProductAddEntitesEvent) {
        count += event.count
    }

    fun changePrice(price: Long): ProductChangePrice =
        ProductChangePrice(id, price)

    fun createProduct(id: UUID, name: String, price: Long): ProductCreateEvent =
        ProductCreateEvent(id, name, price)

    fun addProductEntites(count: Long): ProductAddEntitesEvent =
        ProductAddEntitesEvent(id, count)

    override fun getId(): UUID? = id
}
