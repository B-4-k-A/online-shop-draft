package ru.quipy.shop.delivery.subscribers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.quipy.core.EventSourcingService
import ru.quipy.shop.delivery.Delivery
import ru.quipy.shop.delivery.DeliveryAggregate
import ru.quipy.shop.delivery.DeliveryStatus
import ru.quipy.shop.order.Order
import ru.quipy.shop.order.OrderAggregate
import ru.quipy.shop.order.entities.OrderStatus
import ru.quipy.shop.order.events.OrderChangeStatusEvent
import ru.quipy.shop.user.subscribers.UserToOrderSubscriber
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Component
class DeliveryToOrderSubscriber(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val orderESService: EventSourcingService<UUID, OrderAggregate, Order>,
    private val deliveryESService: EventSourcingService<UUID, DeliveryAggregate, Delivery>
) {
    private val logger: Logger = LoggerFactory.getLogger(UserToOrderSubscriber::class.java)

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(OrderAggregate::class, "delivery::order-subscriber") {
            `when`(OrderChangeStatusEvent::class) { event ->
                if (event.status == OrderStatus.PAID) {
                    var order = orderESService.getState(event.orderId) ?:
                        throw NullPointerException("") //TODO: exception msg
                    deliveryESService.update(order.getId()!!) {
                        it.changeStatus(DeliveryStatus.PAID)
                    }
                }
            }
        }
    }
}