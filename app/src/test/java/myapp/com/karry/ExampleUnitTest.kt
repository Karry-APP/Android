package myapp.com.karry

import myapp.com.karry.entity.Trip
import org.junit.Assert.assertEquals
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testTrip() {
        val trip = Trip("2", "Desc_","Berlin", "Munich")
        assertEquals(trip.id,"2")
        assertEquals(trip.description, "Desc_")
        assertEquals(trip.departureCity, "Berlin")
        assertEquals(trip.destinationCity, "Munich")
    }
}
