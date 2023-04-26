package ru.quipy.shop.product.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
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


@Tag(name = "Product", description = "Product API")
@RestController
@RequestMapping("/product")
class ProductController(
    val productESService: EventSourcingService<UUID, ProductAggregate, Product>,
    val productMongoDao: ProductMongoDao
) {

    @Operation(summary = "Creates new product")
    @ApiResponse(
        responseCode = "200",
        description = "created product",
    )
    @PostMapping
    fun createProduct(
        @RequestParam @Parameter(description = "Product name") name: String,
        @RequestParam @Parameter(description = "product price") price: Long
    ): ProductMongo {
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

    @Operation(summary = "Get product by ID")
    @ApiResponse(
        responseCode = "200",
        description = "product state",
    )
    @GetMapping("/{productId}")
    fun getProduct(@PathVariable @Parameter(description = "product id") productId: UUID) =
        productESService.getState(productId)

    @Operation(summary = "Get all products in stock")
    @ApiResponse(
        responseCode = "200",
        description = "all product states",
    )
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

    @Operation(summary = "Change product price")
    @ApiResponse(
        responseCode = "200",
        description = "updated product entity",
    )
    @PostMapping("/{productId}")
    fun changePrice(
        @PathVariable @Parameter(description = "product id") productId: UUID,
        @RequestParam @Parameter(description = "new price") price: Long
    ) =
        productESService.update(productId) {
            it.changePrice(price)
        }

    @Operation(summary = "Change product count in stock")
    @ApiResponse(
        responseCode = "200",
        description = "updated product entity",
    )
    @PostMapping("/{productId}/add")
    fun addProductEntities(
        @PathVariable @Parameter(description = "product id") productId: UUID,
        @RequestParam @Parameter(description = "entities to add") count: Long
    ) =
        productESService.update(productId) {
            it.addProductEntites(count)
        }
}