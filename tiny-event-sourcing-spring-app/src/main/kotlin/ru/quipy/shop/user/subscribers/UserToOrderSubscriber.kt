package ru.quipy.shop.user.subscribers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.quipy.core.EventSourcingService
import ru.quipy.shop.order.OrderAggregate
import ru.quipy.shop.order.events.OrderCreatedEvent
import ru.quipy.shop.user.User
import ru.quipy.shop.user.UserAggregate
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class UserToOrderSubscriber(
        private val subscriptionsManager: AggregateSubscriptionsManager,
        private val userESService: EventSourcingService<UUID, UserAggregate, User>
) {
    private val logger: Logger = LoggerFactory.getLogger(UserToOrderSubscriber::class.java)

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(OrderAggregate::class, "user::order-subscriber") {
            `when`(OrderCreatedEvent::class) { event ->

                userESService.update(event.userId) {
                    it.addOrder(event.orderId)
                }

                logger.info("Order: ${event.orderId}. Owner: ${event.userId}")
            }
        }
    }
}