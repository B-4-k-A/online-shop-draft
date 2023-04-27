package ru.quipy.shop.cart.controller

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.boot.actuate.trace.http.HttpTrace.Response
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.core.EventSourcingService
import ru.quipy.shop.cart.Cart
import ru.quipy.shop.cart.config.CartAggregate
import ru.quipy.shop.cart.mongo.CartMongo
import ru.quipy.shop.cart.mongo.CartMongoDao
import ru.quipy.shop.product.Product
import ru.quipy.shop.product.config.ProductAggregate
import java.util.IllegalFormatException
import java.util.UUID

@Tag(name = "Cart", description = "Cart API")
@RestController
@RequestMapping("/cart")
class CartController(
    val cartESService: EventSourcingService<UUID, CartAggregate, Cart>,
    var productESService: EventSourcingService<UUID, ProductAggregate, Product>,
    val cartMongoDao : CartMongoDao
) {
    @ExceptionHandler(IllegalStateException::class)
    fun handleException(e: IllegalStateException): String {
        return e.message!!
    }
    @PostMapping
    fun createCart(@RequestParam @Parameter(description = "userId") userId: UUID): CartMongo {
        val id = cartESService.create {
            it.createCart(
                UUID.randomUUID(),
                userId
            )
        }.cartId
        val cartMongo = CartMongo(
            cartId = id,
            cartIdStr = id.toString(),
            userId = userId
        )
        cartMongoDao.save(cartMongo)
        return cartMongo
    }

    @PostMapping("/{cartId}/add")
    fun addProductToCart(
        @PathVariable @Parameter(description = "cart id") cartId: UUID,
        @RequestParam @Parameter(description = "product id") productId: UUID,
        @RequestParam @Parameter(description = "product count") count: Long
    ): Cart? {
        val product = productESService.getState(productId)
        if (product == null)
            throw IllegalStateException("No product with this id")
        if (product.count < count) {
            throw IllegalStateException("Not enough products in stock!")
        } else {
            productESService.update(productId) {
                it.buyoutEntities(count)
            }
            cartESService.update(cartId) {
                it.addProduct(productId, count, product.price)
            }
        }
        return cartESService.getState(cartId)
    }

    @PostMapping("/{cartId}/remove")
    fun removeProductFromCart(
        @PathVariable @Parameter(description = "cart id") cartId: UUID,
        @RequestParam @Parameter(description = "product id") productId: UUID,
        @RequestParam @Parameter(description = "product count") count: Long
    ): Cart? {
        cartESService.update(cartId) {
            it.removeProductFromCart(productId = productId, count = count)
        }
        productESService.update(productId) {
            it.addProductEntites(count)
        }
        return cartESService.getState(cartId)
    }

    @GetMapping("/user/{userId}")
    fun getCartByUserId(@PathVariable @Parameter(description = "user id") userId: UUID): Cart? {
        var mongoCart = cartMongoDao.findOneByUserId(userId)
        return cartESService.getState(mongoCart.cartId)
    }

    @GetMapping("/{cartId}")
    fun getCartById(@PathVariable @Parameter(description = "cart id") cartId: UUID): Cart? {
        return cartESService.getState(cartId)
    }
    @PostMapping("/{cartId}/complete")
    fun completeCart(@PathVariable @Parameter(description = "cart id") cartId: UUID) =
        cartESService.update(cartId) {
            it.completeCart()
        }

}