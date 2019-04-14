package myapp.com.karry.model
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val destinationValue = MutableLiveData<String>()
    val arrivalValue = MutableLiveData<String>()

    fun setDestination(item: String) {
        destinationValue.value = item
    }

    fun setArrival(item: String) {
        arrivalValue.value = item
    }
}