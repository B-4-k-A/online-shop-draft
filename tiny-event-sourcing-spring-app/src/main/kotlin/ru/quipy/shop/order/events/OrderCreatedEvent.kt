package ru.quipy.shop.order.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.order.OrderAggregate

import java.util.UUID

@DomainEvent(name = "ORDER_CREATED_EVENT")
data class OrderCreatedEvent(
    @Getter
    val orderId: UUID,
    @Getter
    val userId: UUID

) : Event<OrderAggregate>(
    name = "ORDER_CREATED_EVENT"
)