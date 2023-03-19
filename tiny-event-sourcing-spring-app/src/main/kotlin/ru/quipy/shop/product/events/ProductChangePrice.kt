package ru.quipy.shop.product.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.product.ProductAggregate

import java.util.UUID

@DomainEvent(name = "PAYMENT_CHANGE_STATUS_EVENT")
class ProductChangePrice(
    @Getter
    val productId: UUID,
    @Getter
    val price: Long
) : Event<ProductAggregate>(
    name = "PAYMENT_CHANGE_STATUS_EVENT"
)