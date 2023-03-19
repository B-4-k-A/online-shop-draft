package ru.quipy.shop.user.controller

import javassist.NotFoundException
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException.NotFound
import ru.quipy.core.EventSourcingService
import ru.quipy.shop.order.Order
import ru.quipy.shop.order.OrderAggregate
import ru.quipy.shop.user.User
import ru.quipy.shop.user.UserAggregate
import ru.quipy.shop.user.events.UserCreatedEvent
import java.util.*


@RestController
@RequestMapping("/users")
class UserController(
    val userESService: EventSourcingService<UUID, UserAggregate, User>,
    val orderESService: EventSourcingService<UUID, OrderAggregate, Order>
) {

    @PostMapping("/")
    fun createUser(
        @RequestParam firstName: String,
        @RequestParam lastName: String,
        @RequestParam phone: String,
        @RequestParam email: String,
        @RequestParam address: String
    ): UserCreatedEvent =
        userESService.create {
            it.createUser(
                firstName,
                lastName,
                phone,
                email,
                address
            )
        }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: UUID) =
        userESService.getState(userId)

    @PostMapping("/{userId}/firstName")
    fun changeFirstName(@PathVariable userId: UUID, @RequestParam firstName: String) =
        userESService.update(userId) { it.changeFirstName(firstName) }

    @PostMapping("/{userId}/lastName")
    fun changeLastName(@PathVariable userId: UUID, @RequestParam lastName: String) =
        userESService.update(userId) { it.changeLastName(lastName) }

    @PostMapping("/{userId}/phone")
    fun changePhone(@PathVariable userId: UUID, @RequestParam phone: String) =
        userESService.update(userId) { it.changePhone(phone) }

    @PostMapping("/{userId}/email")
    fun changeEmail(@PathVariable userId: UUID, @RequestParam email: String) =
        userESService.update(userId) { it.changeEmail(email) }

    @PostMapping("/{userId}/address")
    fun changeAddress(@PathVariable userId: UUID, @RequestParam address: String) =
        userESService.update(userId) { it.changeAddress(address) }

    @PostMapping("/{userId}/order")
    fun addOrder(@PathVariable userId: UUID, @RequestParam orderId: UUID) {
        //TODO: Order should be created here, isn't it? So if it's true, orderId not needed
    }

    @PostMapping("/{userId}/{orderId}")
    fun deleteOrder(@PathVariable userId: UUID, @PathVariable orderId: UUID) {
        val order = orderESService.getState(orderId) ?: throw NotFoundException("Order with $orderId id not found ")
        userESService.update(userId) { it.deleteOrder(order) }
    }


}