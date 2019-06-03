package myapp.com.karry.entity

import androidx.lifecycle.MutableLiveData

class CreateOrderPayload(
    var destinationCity: String = "",
    var departureCity: String = "",
    var arrivalDate: String = "",
    var carryWeight: Float = 0F,
    var carryMaxAmount: Int = 0,
    var carryTaxe: Int = 0
)