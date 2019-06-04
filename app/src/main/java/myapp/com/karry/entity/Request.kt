package myapp.com.karry.entity


class Request(val name: String, val estimatePrice:String, val estimateVolume:String, val estimateWeight:String, val reward:String, val message: String, val creator: String, val tripId: String)

class RequestRating(val user_id: String, val user_firstname: String, val user_lastname: String, val rate: String)