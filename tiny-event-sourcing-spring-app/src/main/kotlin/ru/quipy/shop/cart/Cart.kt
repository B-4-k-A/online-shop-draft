package ru.quipy.shop.cart

import lombok.AllArgsConstructor
import lombok.Getter
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.shop.cart.config.CartAggregate
import ru.quipy.shop.cart.events.AddProductToCartEvent
import ru.quipy.shop.cart.events.CompleteCartEvent
import ru.quipy.shop.cart.events.CreateCartEvent
import ru.quipy.shop.cart.events.RemoveProductFromCartEvent
import ru.quipy.shop.cart.util.ProductInfo
import java.time.Instant
import java.util.UUID

@Getter
@AllArgsConstructor
class Cart : AggregateState<UUID, CartAggregate> {
    private lateinit var id: UUID
    lateinit var userId: UUID
    lateinit var created: Instant
    var completed: Boolean = false
    var products = HashMap<UUID, ProductInfo>()
    var price: Long = 0

    @StateTransitionFunc
    fun createCart(event: CreateCartEvent) {
        id = event.cartId
        userId = event.userId
        created = Instant.now()
    }

    @StateTransitionFunc
    fun addProduct(event: AddProductToCartEvent) {
        if (completed)
            throw IllegalStateException("Cart already completed")
        if (products.containsKey(event.productId)) {
            var productInfo = products.get(event.productId)!!
            productInfo.count = productInfo.count + event.count
            products.put(event.productId, productInfo)
        } else {
            products.put(event.productId, ProductInfo(event.price, event.count))
        }
        price += event.count * event.price
    }

    @StateTransitionFunc
    fun removeProduct(event: RemoveProductFromCartEvent) {
        if (completed)
            throw IllegalStateException("Cart already completed")
        if (!products.containsKey(event.productId)) {
            throw IllegalStateException("No such product in cart !")
        } else if (products.get(event.productId)!!.count < event.count) {
            throw IllegalStateException("Not enough entities in cart !")
        } else {
            var productInfo = products.get(event.productId)!!
            productInfo.count = productInfo.count - event.count
            if (productInfo.count == 0L) {
                products.remove(event.productId)
            } else {
                products.put(event.productId, productInfo)
            }
            price -= productInfo.price * event.count
        }
    }

    @StateTransitionFunc
    fun completeCart(event: CompleteCartEvent) {
        completed = true
    }
    fun createCart(cartId: UUID, userId: UUID): CreateCartEvent =
        CreateCartEvent(cartId, userId)

    fun addProduct(productId: UUID, count: Long, price: Long): AddProductToCartEvent =
        AddProductToCartEvent(id, productId, count, price)

    fun removeProductFromCart(productId: UUID, count: Long): RemoveProductFromCartEvent =
        RemoveProductFromCartEvent(id, productId = productId, count = count)

    fun completeCart(): CompleteCartEvent =
        CompleteCartEvent(price, products, userId)

    override fun getId(): UUID = id
}