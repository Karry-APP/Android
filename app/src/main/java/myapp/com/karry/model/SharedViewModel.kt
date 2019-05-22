package myapp.com.karry.model
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import myapp.com.karry.entity.Trip

class SharedViewModel : ViewModel() {
    val destinationValue = MutableLiveData<String>()
    val arrivalValue = MutableLiveData<String>()

    val tripLisArray: ArrayList<Trip> = arrayListOf()

    fun setDestination(item: String) {
        destinationValue.value = item
    }

    fun setArrival(item: String) {
        arrivalValue.value = item
    }
  
    fun cleanValues() {
        destinationValue.value = ""
        arrivalValue.value = ""
    }

    fun cleanTripsList() {
        tripLisArray.clear()
    }

    fun storeSearchResults(trips: List<Trip>) {
        for (trip in trips) {
            tripLisArray.add(trip)
        }
    }
}