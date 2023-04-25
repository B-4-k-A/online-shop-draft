package ru.quipy.shop.product.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.core.EventSourcingService
import ru.quipy.shop.product.Product
import ru.quipy.shop.product.config.ProductAggregate
import ru.quipy.shop.product.dto.ProductDto
import ru.quipy.shop.product.mongo.ProductMongo
import ru.quipy.shop.product.mongo.ProductMongoDao
import java.util.UUID


@RestController
@RequestMapping("/product")
class ProductController(
    val productESService: EventSourcingService<UUID, ProductAggregate, Product>,
    val productMongoDao: ProductMongoDao
) {

    @PostMapping
    fun createProduct(@RequestParam name: String, @RequestParam price: Long): ProductMongo {
        val id = productESService.create {
            it.createProduct(
                UUID.randomUUID(),
                name,
                price
            )
        }.productId
        val productMongo = ProductMongo(
            aggregateId = id,
            aggregateIdStr = id.toString(),
            name = name
        )
        productMongoDao.save(productMongo)
        return productMongo
    }

    @GetMapping("/{productId}")
    fun getProduct(@PathVariable productId: UUID) =
        productESService.getState(productId)

    @GetMapping
    fun getProducts(): ArrayList<ProductDto> {
        var result = ArrayList<ProductDto>()
        var productIds = productMongoDao.findAll()
        for (product in productIds) {
            var state = productESService.getState(product.aggregateId)!!
            result.add(ProductDto(
                id = product.aggregateId,
                name = product.name,
                count = state.count,
                price = state.price
            ))
        }
        return result
    }

    @PostMapping("/{productId}")
    fun changeStatus(@PathVariable productId: UUID, @RequestParam price: Long) =
        productESService.update(productId) {
            it.changePrice(price)
        }

    @PostMapping("/{productId}/add")
    fun addProductEntities(@PathVariable productId: UUID, @RequestParam count: Long) =
        productESService.update(productId) {
            it.addProductEntites(count)
        }
}