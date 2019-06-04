package myapp.com.karry.entity

class UserRequest (
    val id: String,
    val tripId: String,
    val name: String,
    val message: String,

    val estimateWeight: String,
    val estimateVolume: String,
    val estimatePrice: String,

    val reward: Int,

    val creator: String
)


/*
        "status": "waiting",
        "_id": "5ceaac768b135e2854f85066",
        "name": "Je souahiterais cordialement baiser tes morts",
        "estimateWeight": "3",
        "estimateVolume": "3",
        "estimatePrice": "3",
        "reward": "4",
        "message": "Voilà mon message héhé :)",
        "creator": "5ca5e8c4a962640017cf97be",
        "tripId": "5ca62eef5076880017c31081",
        "__v": 0,
        "id": "5ceaac768b135e2854f85066"
 */