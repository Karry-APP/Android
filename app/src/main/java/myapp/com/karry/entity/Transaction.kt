package myapp.com.karry.entity

class Transaction (
    val id: String,
    val description: String,
    val name: String,
    val price: String,
    val sharedImage: ArrayList<SharedImage>,
    val author: User
)