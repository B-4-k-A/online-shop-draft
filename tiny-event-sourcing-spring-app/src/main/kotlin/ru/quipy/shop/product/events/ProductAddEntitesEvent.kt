package ru.quipy.shop.product.events

import lombok.Getter
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import ru.quipy.shop.product.config.ProductAggregate
import java.util.UUID

@DomainEvent(name = "PRODUCT_ADD_ENTITIES_EVENT")
class ProductAddEntitesEvent(
    @Getter
    val productId: UUID,
    @Getter
    val count: Long,
): Event<ProductAggregate>(
    name = "PRODUCT_ADD_ENTITIES_EVENT"
)