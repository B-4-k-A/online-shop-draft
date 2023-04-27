package ru.quipy.shop.cart.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.cart.config.CartAggregate
import java.util.UUID

@DomainEvent(name = "REMOVE_PRODUCT_FROM_CART_EVENT")
class RemoveProductFromCartEvent(
    @Getter
    val cartId: UUID,

    @Getter
    val productId: UUID,

    @Getter
    val count: Long
) : Event<CartAggregate>(
    name = "REMOVE_PRODUCT_FROM_CART_EVENT"
)