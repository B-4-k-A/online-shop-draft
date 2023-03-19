package ru.quipy.shop.delivery.events


import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.delivery.DeliveryAggregate
import ru.quipy.shop.delivery.entity.DeliveryEntity

import java.util.Date
import java.util.UUID

@Getter
@DomainEvent(name = "DELIVERY_CREATE_EVENT")
class DeliveryCreateEvent(
    val deliveryId: UUID,
    var address: String,
    var price: Long,
    var date: Date,
    var orderId: UUID
) : Event<DeliveryAggregate>(
    name = "DELIVERY_CREATE_EVENT"
)