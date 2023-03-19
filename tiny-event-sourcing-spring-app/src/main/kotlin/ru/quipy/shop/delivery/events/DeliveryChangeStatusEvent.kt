package ru.quipy.shop.delivery.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.delivery.DeliveryAggregate
import ru.quipy.shop.delivery.DeliveryStatus

import java.util.UUID

@Getter
@DomainEvent(name = "DELIVERY_CHANGE_STATUS_EVENT")
class DeliveryChangeStatusEvent(
    val deliveryId: UUID,
    var status: DeliveryStatus
) : Event<DeliveryAggregate>(
    id = deliveryId,
    name = "DELIVERY_CHANGE_STATUS_EVENT")
