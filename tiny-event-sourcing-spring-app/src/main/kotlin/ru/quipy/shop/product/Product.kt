package ru.quipy.shop.product

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.Setter
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.shop.product.config.ProductAggregate
import ru.quipy.shop.product.events.ProductAddEntitesEvent
import ru.quipy.shop.product.events.ProductBuyoutEvent
import ru.quipy.shop.product.events.ProductChangePriceEvent
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
    fun changePrice(event: ProductChangePriceEvent) {
        price = event.price
    }

    @StateTransitionFunc
    fun addEntities(event: ProductAddEntitesEvent) {
        count += event.count
    }

    @StateTransitionFunc
    fun buyout(event: ProductBuyoutEvent) {
        if (event.entities > count) {
            throw IllegalStateException("not enough product entities available in stock !")
        }
    }

    fun changePrice(price: Long): ProductChangePriceEvent =
        ProductChangePriceEvent(id, price)

    fun createProduct(id: UUID, name: String, price: Long): ProductCreateEvent =
        ProductCreateEvent(id, name, price)

    fun addProductEntites(count: Long): ProductAddEntitesEvent =
        ProductAddEntitesEvent(id, count)

    fun buyoutEntities(count: Long): ProductBuyoutEvent =
        ProductBuyoutEvent(id, count)

    override fun getId(): UUID? = id
}
