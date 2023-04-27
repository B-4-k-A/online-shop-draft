package ru.quipy.shop.cart.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.cart.config.CartAggregate
import ru.quipy.shop.cart.util.ProductInfo
import java.util.UUID

@DomainEvent(name = "COMPLETE_CART_EVENT")
class CompleteCartEvent(
    @Getter
    val price: Long,

    @Getter
    val products: HashMap<UUID, ProductInfo>,

    @Getter
    val userId: UUID
)  : Event<CartAggregate>(
name = "COMPLETE_CART_EVENT"
)