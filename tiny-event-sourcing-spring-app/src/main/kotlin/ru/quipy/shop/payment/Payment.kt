package ru.quipy.shop.payment

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.shop.order.OrderAggregate
import ru.quipy.shop.order.events.OrderCreatedEvent
import ru.quipy.shop.payment.entities.PaymentStatus
import ru.quipy.shop.payment.events.PaymentChangeStatusEvent
import ru.quipy.shop.payment.events.PaymentCreateEvent
import ru.quipy.shop.payment.events.PaymentSetDateEvent

import java.util.Date
import java.util.Optional
import java.util.UUID

class Payment : AggregateState<UUID, PaymentAggregate> {
    private lateinit var id: UUID
    private lateinit var orderId: UUID
    private var paymentDate: Optional<Date> = Optional.empty()
    private var totalPrice: Long = 0
    private var status: PaymentStatus = PaymentStatus.AWAITING
    override fun getId(): UUID = id
    fun getOrderId(): UUID = orderId

    @StateTransitionFunc
    fun createPayment(event: PaymentCreateEvent) {
        id = event.paymentId
        orderId = event.orderId
        totalPrice = event.totalPrice
    }

    @StateTransitionFunc
    fun changeStatus(event: PaymentChangeStatusEvent) {
        status = event.status
    }

    @StateTransitionFunc
    fun setPaymentDate(event: PaymentSetDateEvent) {
        paymentDate = Optional.of(event.date)
    }

    fun createPayment(id: UUID, orderId: UUID, totalPrice: Long): PaymentCreateEvent =
        PaymentCreateEvent(id, orderId, totalPrice)

    fun changeStatus(status: PaymentStatus): PaymentChangeStatusEvent =
        PaymentChangeStatusEvent(id, status)

    fun setPaymentDate(date: Date): PaymentSetDateEvent =
        if (paymentDate.isPresent || status == PaymentStatus.SUCCESSFULL)
            throw IllegalStateException("Payment was already completed")
        else
            PaymentSetDateEvent(date, id)

}
