package ru.quipy.shop.product.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.product.config.ProductAggregate

import java.util.UUID

@DomainEvent(name = "PRODUCT_CREATE_EVENT")
class ProductCreateEvent(
    @Getter
    val productId: UUID,
    @Getter
    val productName: String,
    @Getter
    val price: Long
) : Event<ProductAggregate>(
    name = "PRODUCT_CREATE_EVENT"
)