package ru.quipy.shop.delivery

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.shop.delivery.entity.DeliveryEntity
import ru.quipy.shop.delivery.events.DeliveryChangeDateEvent
import ru.quipy.shop.delivery.events.DeliveryChangeStatusEvent
import ru.quipy.shop.delivery.events.DeliveryCreateEvent
import java.util.*


class Delivery : AggregateState<UUID, DeliveryAggregate> {

    private lateinit var deliveryEntity: DeliveryEntity
    private lateinit var orderId: UUID
    private var status: DeliveryStatus = DeliveryStatus.BOOKED

    override fun getId(): UUID = deliveryEntity.id
    fun getOrderId(): UUID = orderId

    @StateTransitionFunc
    fun createDelivery(event: DeliveryCreateEvent) {
        deliveryEntity = DeliveryEntity(event.deliveryId, event.address, event.price, event.date)
        orderId = event.orderId
    }

    @StateTransitionFunc
    fun changeStatus(event: DeliveryChangeStatusEvent) {
        deliveryEntity.status = event.status
    }

    @StateTransitionFunc
    fun changeDate(event: DeliveryChangeDateEvent) {
        deliveryEntity.date = event.date
    }

    fun createDelivery(id: UUID, address: String, price: Long, date: Date, orderId: UUID): DeliveryCreateEvent =
        DeliveryCreateEvent(id, address, price, date, orderId)

    fun changeStatus(status: DeliveryStatus): DeliveryChangeStatusEvent =
        //проверка на корректный статус
        DeliveryChangeStatusEvent(deliveryEntity.id, status)

    fun changeDate(date: Date): DeliveryChangeDateEvent {
        if (deliveryEntity.status == DeliveryStatus.PAID || deliveryEntity.status == DeliveryStatus.DELIVERED)
            throw IllegalStateException ("Delivery already completed")
        return DeliveryChangeDateEvent (deliveryEntity.id, date)
    }
}
