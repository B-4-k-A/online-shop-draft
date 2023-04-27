package ru.quipy.shop.cart.util

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.Setter
import lombok.Value

@Getter
@AllArgsConstructor
class ProductInfo (
    var price: Long = 0,
    @Setter
    var count: Long = 0
)