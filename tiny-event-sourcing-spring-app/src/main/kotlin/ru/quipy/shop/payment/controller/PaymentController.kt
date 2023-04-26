package ru.quipy.shop.payment.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import ru.quipy.shop.payment.Payment
import ru.quipy.shop.payment.config.PaymentAggregate
import ru.quipy.shop.payment.entities.PaymentStatus
import java.util.*


@RestController
@RequestMapping("/payments")
class PaymentController(
    val paymentESService: EventSourcingService<UUID, PaymentAggregate, Payment>
) {

    @PostMapping
    fun createPayment(@RequestParam orderId: UUID, @RequestParam totalPrice: Long) =
        paymentESService.create {
            it.createPayment(
            UUID.randomUUID(),
                orderId,
                totalPrice
            )
        }

    @GetMapping("/{paymentId}")
    fun getPayment(@PathVariable paymentId: UUID) =
        paymentESService.getState(paymentId)

    @PostMapping("/{paymentId}/status")
    fun changeStatus(@PathVariable paymentId: UUID, @RequestParam status: PaymentStatus) =
        paymentESService.update(paymentId) { it.changeStatus(status)}


}