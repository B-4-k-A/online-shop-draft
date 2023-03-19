package ru.quipy.shop.product.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.annotation.RequestScope
import ru.quipy.core.EventSourcingService
import ru.quipy.shop.product.Product
import ru.quipy.shop.product.ProductAggregate
import ru.quipy.shop.product.events.ProductCreateEvent
import java.util.UUID


@RestController
@RequestMapping("/product")
class ProductController(
    val productESService: EventSourcingService<UUID, ProductAggregate, Product>
) {

    @PostMapping("")
    fun createProduct(@RequestParam name: String, @RequestParam price: Long) =
        productESService.create {
            it.createProduct(
                UUID.randomUUID(),
                name,
                price
            )
        }

    @GetMapping("/{productId}")
    fun getProduct(@PathVariable productId: UUID) =
        productESService.getState(productId)

    @PutMapping("/{productId}/")
    fun changeStatus(@PathVariable productId: UUID, @RequestParam price: Long) =
        productESService.update(productId) {
            it.changePrice(price)
        }

}