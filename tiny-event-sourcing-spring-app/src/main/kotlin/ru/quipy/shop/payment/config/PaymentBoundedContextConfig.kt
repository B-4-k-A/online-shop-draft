package ru.quipy.shop.payment.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.shop.delivery.Delivery
import ru.quipy.shop.delivery.DeliveryAggregate
import ru.quipy.shop.payment.Payment
import ru.quipy.shop.payment.PaymentAggregate
import java.util.*

@Configuration
class PaymentBoundedContextConfig {
    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Bean
    fun paymentEsService(): EventSourcingService<UUID, PaymentAggregate, Payment> =
            eventSourcingServiceFactory.create()
}