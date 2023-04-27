package ru.quipy.shop.cart.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.cart.config.CartAggregate
import java.util.UUID

@DomainEvent(name = "CREATE_CART_EVENT")
class CreateCartEvent(
    @Getter
    val cartId: UUID,

    @Getter
    val userId: UUID
) : Event<CartAggregate>(
    name = "CREATE_CART_EVENT"
)