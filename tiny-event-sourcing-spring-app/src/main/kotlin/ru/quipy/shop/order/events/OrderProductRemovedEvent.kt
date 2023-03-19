package ru.quipy.shop.order.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.order.OrderAggregate
import ru.quipy.shop.product.Product

import java.util.UUID


@DomainEvent(name = "PRODUCT_REMOVED_EVENT")
class OrderProductRemovedEvent(
    @Getter
    private val orderId: UUID,
    @Getter val product: Product
) : Event<OrderAggregate>(
    name = "PRODUCT_REMOVED_EVENT"
)