package ru.quipy.shop.order

import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.shop.order.entities.OrderStatus
import ru.quipy.shop.order.events.*
import ru.quipy.shop.product.Product
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.HashMap


class Order : AggregateState<UUID, OrderAggregate> {
    private lateinit var id: UUID
    lateinit var userId: UUID
    private val products: HashMap<UUID, Int> = HashMap()
    var price: Long = 0L
    var assemblingTime: Long = 0 // это хз, может снести
    var status: OrderStatus = OrderStatus.COLLECTING
    var deliveryId: Optional<UUID> = Optional.empty()
    var paymentId: Optional<UUID> = Optional.empty()

    @StateTransitionFunc
    fun addProduct(event: OrderProductAddedEvent) {
        val productId = event.productId
        products[productId] = products.getOrDefault(productId, 0) + 1
        price += event.price * event.quantity
    }

    @StateTransitionFunc
    fun removeProduct(event: OrderProductRemovedEvent) {
        val productId = event.product.getId()!!
        val quantity = products[productId] ?: throw IllegalArgumentException("No such product in order $id")
        if (quantity == 1) {
            products.remove(productId)
        } else {
            products[productId] = products[productId]!! - 1
        }
        price -= event.product.price
    }

    @StateTransitionFunc
    fun changeStatus(event: OrderChangeStatusEvent) {
        status = event.status;
    }

    @StateTransitionFunc
    fun orderCreated(event: OrderCreatedEvent) {
        id = event.id
        userId = event.userId
    }

    @StateTransitionFunc
    fun deliverySet(event: OrderDeliverySetEvent) {
        deliveryId = Optional.of(event.deliveryId)
    }

    @StateTransitionFunc
    fun paymentSet(event: OrderPaymentSetEvent) {
        paymentId = Optional.of(event.paymentId)
    }

    fun createOrder(userId: UUID): OrderCreatedEvent =
        OrderCreatedEvent(UUID.randomUUID(), userId)

    fun setDelivery(deliveryId: UUID): OrderDeliverySetEvent =
        if (this.deliveryId.isPresent)
            throw IllegalStateException("Delivery already set in order $id")
        else
            OrderDeliverySetEvent(this.id, deliveryId)

    fun setPayment(paymentId: UUID): OrderPaymentSetEvent =
        if (this.paymentId.isPresent)
            throw IllegalStateException("Payment already set in order $id")
        else
            OrderPaymentSetEvent(this.id, paymentId)

    fun addProduct(productId: UUID, quantity:Int, price: Long): OrderProductAddedEvent =
        OrderProductAddedEvent(this.id, productId, quantity, price)


    fun deleteProduct(product: Product): OrderProductRemovedEvent =
        if (!products.containsKey(product.getId()))
            throw IllegalStateException("no such product in order")
        else
            OrderProductRemovedEvent(this.id, product)

    fun changeStatus(status: OrderStatus): OrderChangeStatusEvent {
        if (this.status == OrderStatus.COLLECTING && status == OrderStatus.BOOKED) {
            if (products.size > 0)
                return OrderChangeStatusEvent(status, this.id)
            else
                throw java.lang.IllegalStateException("No Products in the order!")
        }
        //тут логику потом добавить на другие статусы
        return OrderChangeStatusEvent(status, this.id)
    }

    override fun getId(): UUID? = id

}
