package myapp.com.karry.modules

class ApiManager {

    class URL {
        companion object {
            const val BASE = "https://karry-dev.herokuapp.com"

            // USERS
            const val USER_LOGIN = "$BASE/users/login"
            const val USER_REGISTER = "$BASE/users"
            const val USER_LOGOUT = "$BASE/users/me/token"
            fun USER_TRIPS(userId: String?): String {
                return "$BASE/users/$userId/trips"
            }

            // TRIPS
            const val TRIP_SEARCH = "$BASE/trips/search"
            const val TRIP_CREATE = "$BASE/trips"
        }
    }
}