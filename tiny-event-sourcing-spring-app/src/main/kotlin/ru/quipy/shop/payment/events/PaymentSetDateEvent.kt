package ru.quipy.shop.payment.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.payment.PaymentAggregate
import java.util.*

@DomainEvent(name = "PAYMENT_SET_DATE")
class PaymentSetDateEvent(
    @Getter
    val date: Date,
    @Getter
    val paymentId: UUID
) : Event<PaymentAggregate>(
    name = "PAYMENT_SET_DATE"
)