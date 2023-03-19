package ru.quipy.shop.order.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@AllArgsConstructor
data class Product (
    val id: UUID,
    val name: String,
    @Setter
    val price: Long
)