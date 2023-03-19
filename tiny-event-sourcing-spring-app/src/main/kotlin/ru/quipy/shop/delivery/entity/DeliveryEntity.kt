package ru.quipy.shop.delivery.entity

import lombok.Getter
import lombok.Setter
import ru.quipy.shop.delivery.DeliveryStatus
import java.util.*

@Getter
data class DeliveryEntity(
    val id: UUID,
    private val address: String,
    private val price: Long,
    @Setter var date: Date,
    @Setter var status: DeliveryStatus = DeliveryStatus.BOOKED
)
