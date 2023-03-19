package ru.quipy.shop.order.events


import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.order.OrderAggregate

import java.util.UUID

@DomainEvent(name = "ORDER_PAYMENT_LINKED")
class OrderPaymentSetEvent(
    @Getter
    public val orderId: UUID,
    @Getter
    public val paymentId: UUID
) : Event<OrderAggregate>(
    name = "ORDER_PAYMENT_LINKED"
)