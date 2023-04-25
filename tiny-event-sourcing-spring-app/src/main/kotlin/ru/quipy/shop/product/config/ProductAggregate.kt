package ru.quipy.shop.product.config;

import ru.quipy.core.annotations.AggregateType;
import ru.quipy.domain.Aggregate;

@AggregateType(aggregateEventsTableName = "products")
class ProductAggregate : Aggregate