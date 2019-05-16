package myapp.com.karry.modules

class ApiManager {

    class URL {
        companion object {
            const val BASE = "https://karry-api-thomasoumar.c9users.io"

            // USERS
            const val USER_LOGIN = "$BASE/users/login"
            const val USER_LOGIN_GOOGLE = "$BASE/auth/google"
            const val USER_LOGIN_FACEBOOK = "$BASE/auth/facebook"
            const val USER_REGISTER = "$BASE/users/register"
            const val USER_LOGOUT = "$BASE/users/me/token"
            fun USER_UPDATE(userId: String?): String = "$BASE/users/$userId"
            fun USER_TRIPS(userId: String?): String = "$BASE/users/$userId/trips"

            // TRIPS
            const val TRIP_SEARCH = "$BASE/trips/search"
            const val TRIP_CREATE = "$BASE/trips"
            fun TRIP_DETAIL(tripId: String?): String = "$BASE/trips/$tripId"
        }
    }
}