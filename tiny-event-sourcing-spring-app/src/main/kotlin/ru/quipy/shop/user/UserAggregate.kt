package ru.quipy.shop.user;

import ru.quipy.core.annotations.AggregateType;
import ru.quipy.domain.Aggregate;

@AggregateType(aggregateEventsTableName = "user")
class UserAggregate : Aggregate

