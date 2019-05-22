package myapp.com.karry.modules

class ApiManager {

    class URL {
        companion object {
            const val BASE = "http://api.karry.fr"

            // USERS
            const val USER_LOGIN = "$BASE/users/login"
            const val USER_REGISTER = "$BASE/users/register"
            const val ME = "$BASE/users/me"
            const val USER_PATCH = "$BASE/users/"
            const val USER_LOGOUT = "$BASE/users/me/token"
            fun USER_TRIPS(userId: String?): String {
                return "$BASE/users/$userId/trips"
            }

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