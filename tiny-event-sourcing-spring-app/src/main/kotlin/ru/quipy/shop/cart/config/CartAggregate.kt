package ru.quipy.shop.cart.config

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType(aggregateEventsTableName = "cart")
class CartAggregate : Aggregate