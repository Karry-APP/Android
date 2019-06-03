package myapp.com.karry.modules

class ApiManager {

    class URL {
        companion object {
            const val BASE = "https://api.karry.fr"

            // USERS
            const val USER_LOGIN = "$BASE/auth/login"
            const val USER_REGISTER = "$BASE/auth/register"
            const val ME = "$BASE/users/me"
            const val USER_PATCH = "$BASE/users/"
            const val USER_LOGOUT = "$BASE/users/me/token"
            const val USER_TRIPS = "$BASE/users/me/trips"
            fun USER_LOAD(userId: String?): String {
                return "$BASE/users/$userId"
            }

            // REQUESTS
            fun USER_REQUESTS(id: String) = "$BASE/requests/$id"
            const val USER_JOIN_LIST = ""

            // TRIPS
            const val TRIP_SEARCH = "$BASE/trips/search"
            const val TRIP_CREATE = "$BASE/trips"
            fun TRIP_DETAIL(tripId: String?): String {
                return "$BASE/trips/$tripId"
            }

            //ORDER
            const val REQUESTS_CREATE = "$BASE/requests"
        }
    }
}