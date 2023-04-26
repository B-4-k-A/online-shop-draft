package ru.quipy.shop.payment

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.shop.payment.config.PaymentAggregate
import ru.quipy.shop.payment.entities.PaymentStatus
import ru.quipy.shop.payment.events.PaymentChangeStatusEvent
import ru.quipy.shop.payment.events.PaymentCreateEvent
import java.time.Instant

import java.util.UUID

class Payment : AggregateState<UUID, PaymentAggregate> {
    private lateinit var id: UUID
    private lateinit var orderId: UUID
    private lateinit var created: Instant
    private lateinit var updated: Instant
    private var totalPrice: Long = 0
    private var status: PaymentStatus = PaymentStatus.CREATED
    override fun getId(): UUID = id
    fun getOrderId(): UUID = orderId

    @StateTransitionFunc
    fun createPayment(event: PaymentCreateEvent) {
        id = event.paymentId
        orderId = event.orderId
        totalPrice = event.totalPrice
        created = Instant.now()
        updated = Instant.now()
    }

    @StateTransitionFunc
    fun changeStatus(event: PaymentChangeStatusEvent) {
        status = event.status
        updated = Instant.now()
    }

    fun createPayment(id: UUID, orderId: UUID, totalPrice: Long): PaymentCreateEvent =
        PaymentCreateEvent(id, orderId, totalPrice)

    fun changeStatus(status: PaymentStatus): PaymentChangeStatusEvent =
        PaymentChangeStatusEvent(id, status)

}
