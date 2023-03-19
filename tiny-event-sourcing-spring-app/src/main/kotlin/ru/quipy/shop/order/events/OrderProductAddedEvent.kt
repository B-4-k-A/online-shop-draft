package ru.quipy.shop.order.events


import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.order.entities.Product
import ru.quipy.shop.order.OrderAggregate

import java.util.UUID


@DomainEvent(name = "PRODUCT_ADDED_EVENT")
class OrderProductAddedEvent(
    @Getter
    private val orderId: UUID,
    @Getter val productId: UUID,
    val quantity: Int,
    val price: Long
) : Event<OrderAggregate>(
    name = "PRODUCT_ADDED_EVENT"
)