package myapp.com.karry.entity

import com.google.gson.annotations.SerializedName

class Trip (
    val id: String,
    val description: String,
    val departureCity:String,
    val destinationCity: String,
    val departureCountry: String,
    val destinationCountry: String,
    val carryWeight: String,
    val carryMaxAmount: String,
    val carryTaxe: String,
    val creator: String,
    val joinList: ArrayList<User>
)