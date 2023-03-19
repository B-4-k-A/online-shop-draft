package ru.quipy.shop.delivery.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.delivery.DeliveryAggregate
import java.util.*

@Getter
@DomainEvent(name = "DELIVERY_CHANGE_DATE_EVENT")
class DeliveryChangeDateEvent(
    private val deliveryId: UUID,
    val date: Date

) : Event<DeliveryAggregate>(
    id = deliveryId,
    name = "DELIVERY_CHANGE_DATE_EVENT")