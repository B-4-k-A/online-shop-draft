package ru.quipy.shop.delivery.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.shop.delivery.Delivery
import ru.quipy.shop.delivery.DeliveryAggregate
import ru.quipy.shop.order.Order
import ru.quipy.shop.order.OrderAggregate
import java.util.*

@Configuration
class DeliveryBoundedContextConfig {
    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun deliveryEsService(): EventSourcingService<UUID, DeliveryAggregate, Delivery> =
            eventSourcingServiceFactory.create()
}