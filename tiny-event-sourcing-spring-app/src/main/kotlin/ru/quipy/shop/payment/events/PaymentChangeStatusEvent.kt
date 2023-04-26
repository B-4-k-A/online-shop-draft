package ru.quipy.shop.payment.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.payment.config.PaymentAggregate
import ru.quipy.shop.payment.entities.PaymentStatus

import java.util.UUID

@DomainEvent(name = "PAYMENT_CHANGE_STATUS_EVENT")
class PaymentChangeStatusEvent(
    @Getter
    val paymentId: UUID,
    @Getter
    val status: PaymentStatus
) : Event<PaymentAggregate>(
    name = "PAYMENT_CHANGE_STATUS_EVENT"
)