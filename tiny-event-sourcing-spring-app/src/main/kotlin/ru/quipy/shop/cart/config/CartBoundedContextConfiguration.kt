package ru.quipy.shop.cart.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.shop.cart.Cart
import java.util.UUID

@Configuration
class CartBoundedContextConfiguration {
    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun cartEsService(): EventSourcingService<UUID, CartAggregate, Cart> =
        eventSourcingServiceFactory.create()
}