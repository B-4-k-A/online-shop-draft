package ru.quipy.shop.cart.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.cart.config.CartAggregate
import java.util.UUID

@DomainEvent(name = "ADD_PRODUCT_TO_CART_EVENT")
class AddProductToCartEvent(
    @Getter
    val cartId: UUID,

    @Getter
    val productId: UUID,

    @Getter
    val count: Long,

    @Getter
    val price: Long
) : Event<CartAggregate>(
    name = "ADD_PRODUCT_TO_CART_EVENT"
)