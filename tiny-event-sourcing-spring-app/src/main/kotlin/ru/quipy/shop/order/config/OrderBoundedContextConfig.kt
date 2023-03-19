package ru.quipy.shop.order.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.shop.order.Order
import ru.quipy.shop.order.OrderAggregate
import java.util.*

@Configuration
class OrderBoundedContextConfig {
    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun orderEsService(): EventSourcingService<UUID, OrderAggregate, Order> =
            eventSourcingServiceFactory.create()
}

