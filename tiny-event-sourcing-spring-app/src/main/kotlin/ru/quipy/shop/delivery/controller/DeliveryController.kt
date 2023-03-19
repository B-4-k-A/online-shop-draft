package ru.quipy.shop.delivery.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.core.EventSourcingService
import ru.quipy.shop.delivery.Delivery
import ru.quipy.shop.delivery.DeliveryAggregate
import ru.quipy.shop.delivery.DeliveryStatus
import ru.quipy.shop.delivery.events.DeliveryCreateEvent
import java.util.*


@RestController
@RequestMapping("/deliveries")
class DeliveryController(
    val deliveryESService: EventSourcingService<UUID, DeliveryAggregate, Delivery>,
) {

    @PostMapping("")
    fun createDelivery(
        @RequestParam address: String,
        @RequestParam price: Long,
        @RequestParam orderId: UUID
    ): DeliveryCreateEvent =
        deliveryESService.create {
            it.createDelivery(
                UUID.randomUUID(),
                address,
                price,
                Date(),
                orderId
            )
        }

    @GetMapping("/{deliveryId}")
    fun getDelivery(@PathVariable deliveryId: UUID) : Delivery? =
        deliveryESService.getState(deliveryId)

    @PutMapping("/{deliveryId}/changeDate")
    fun changeDate(
        @PathVariable deliveryId: UUID,
        @RequestParam status: DeliveryStatus
    ) = deliveryESService.update(deliveryId) { it.changeStatus(status)}
}