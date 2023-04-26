package ru.quipy.shop.product.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.product.config.ProductAggregate

import java.util.UUID

@DomainEvent(name = "PRODUCT_CHANGE_PRICE_EVENT")
class ProductChangePriceEvent(
    @Getter
    val productId: UUID,
    @Getter
    val price: Long
) : Event<ProductAggregate>(
    name = "PRODUCT_CHANGE_PRICE_EVENT"
)