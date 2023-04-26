package ru.quipy.shop.payment.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.payment.config.PaymentAggregate

import java.util.UUID

@DomainEvent(name = "PAYMENT_CREATE_EVENT")
class PaymentCreateEvent(
    @Getter
    val paymentId: UUID,
    @Getter
    val orderId: UUID,
    @Getter
    val totalPrice: Long
) : Event<PaymentAggregate>(
    name = "PAYMENT_CREATE_EVENT"
)