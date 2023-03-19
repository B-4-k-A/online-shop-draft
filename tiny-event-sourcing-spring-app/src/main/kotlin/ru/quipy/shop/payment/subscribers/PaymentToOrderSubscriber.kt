package ru.quipy.shop.payment.subscribers

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.quipy.core.EventSourcingService
import ru.quipy.shop.delivery.Delivery
import ru.quipy.shop.delivery.DeliveryAggregate
import ru.quipy.shop.order.Order
import ru.quipy.shop.order.OrderAggregate
import ru.quipy.shop.order.entities.OrderStatus
import ru.quipy.shop.order.events.OrderChangeStatusEvent
import ru.quipy.shop.payment.Payment
import ru.quipy.shop.payment.PaymentAggregate
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.UUID
import javax.annotation.PostConstruct

class PaymentToOrderSubscriber(
    private val subscriptionsManager: AggregateSubscriptionsManager,
    private val orderESService: EventSourcingService<UUID, OrderAggregate, Order>,
    private val paymentESService: EventSourcingService<UUID, PaymentAggregate, Payment>,
    private val deliveryESService: EventSourcingService<UUID, DeliveryAggregate, Delivery>
) {
    private val logger: Logger = LoggerFactory.getLogger(PaymentToOrderSubscriber::class.java)

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(OrderAggregate::class, "payment::order-subscriber") {
            `when`(OrderChangeStatusEvent::class) { event ->
//                if(event.status == OrderStatus.BOOKED) {
//                    val order = paymentESService.getState(event.orderId)
//                    val payment = Payment() // возвращает объект паймент
//                    payment.createPayment() // создает ивент и возвращает
//                }
                if (event.status == OrderStatus.BOOKED) {
//                    var order = orderESService.getState(event.orderId)
//                    if (order != null) {
//                        paymentESService.update(order.getPaymentId()) {
//                            it.changeStatus(PaymentStatus.AWAITING)
//                        }
                }
            }
        }

    }
}