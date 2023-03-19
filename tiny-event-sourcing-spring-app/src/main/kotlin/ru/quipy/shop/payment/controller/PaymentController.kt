package ru.quipy.shop.payment.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import ru.quipy.shop.payment.Payment
import ru.quipy.shop.payment.PaymentAggregate
import ru.quipy.shop.payment.entities.PaymentStatus
import ru.quipy.shop.payment.events.PaymentCreateEvent
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

    @PutMapping("/{paymentId}/status")
    fun changeStatus(@PathVariable paymentId: UUID, @RequestParam status: PaymentStatus) =
        paymentESService.update(paymentId) { it.changeStatus(status)}

    @PutMapping("/{paymentId}")
    fun setPaymentDate(@PathVariable paymentId: UUID, @RequestParam date: Date) =
        paymentESService.update(paymentId) { it.setPaymentDate(date)}
}