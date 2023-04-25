package ru.quipy.shop.product.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.shop.product.Product
import java.util.*

@Configuration
class ProductBoundedContextConfig {
    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun productEsService(): EventSourcingService<UUID, ProductAggregate, Product> =
            eventSourcingServiceFactory.create()
}