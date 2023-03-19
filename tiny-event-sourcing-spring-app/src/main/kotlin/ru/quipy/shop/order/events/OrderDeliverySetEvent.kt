package ru.quipy.shop.order.events


import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.order.OrderAggregate

import java.util.UUID

@DomainEvent(name = "ORDER_DELIVERY_SET")
class OrderDeliverySetEvent(
    @Getter
    val orderId: UUID,
    @Getter
    val deliveryId: UUID
) : Event<OrderAggregate>(
    name = "ORDER_DELIVERY_SET"
)