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
    val carryVolume: String,
    val carryTaxe: String,
    val owner: User,
    val joinList: ArrayList<User>,
    val arrivalDate: String
)