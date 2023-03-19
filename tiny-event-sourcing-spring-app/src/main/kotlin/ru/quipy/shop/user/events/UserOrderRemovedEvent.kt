package ru.quipy.shop.user.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.order.Order
import ru.quipy.shop.user.UserAggregate

import java.util.UUID

@Getter
@DomainEvent(name = "USER_ORDER_REMOVED_EVENT")
class UserOrderRemovedEvent(
    val userId: UUID,
    val order: Order

) : Event<UserAggregate>(
    name = "USER_ORDER_REMOVED_EVENT"
)