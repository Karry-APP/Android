package myapp.com.karry.model
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import myapp.com.karry.entity.*

class SharedViewModel : ViewModel() {
    val destinationValue = MutableLiveData<String>()
    val arrivalValue = MutableLiveData<String>()
    val transactionId = MutableLiveData<String>()
    private val defaultSharedImageList: ArrayList<SharedImage> = arrayListOf()

    private val defaultAuthor: User = User("toto", "Nicolas", "Leroy", "0646862158", "nico@kkarry.fr", "https://png.pngtree.com/element_origin_min_pic/17/09/18/d555144313d6d69a8820a3baaf5d81fe.jpg", "", "Pas de description encore")
    private val defaultTransaction = Transaction("test", "Hahahahhahahahha", "Huilde d'olive", "24, 30 €", defaultSharedImageList, defaultAuthor)
    val tripListArray: ArrayList<Trip> = arrayListOf()
    val transactionListArray: ArrayList<Transaction> = arrayListOf()
    val backerListArray: ArrayList<User> = arrayListOf()

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
        tripListArray.clear()
    }
    fun cleanTransactionList() {
        transactionListArray.clear()
    }

    fun cleanBakcerList() {
        backerListArray.clear()
    }

    fun storeTrips(trips: List<Trip>) {
        for (trip in trips) {
            tripListArray.add(trip)
        }
    }

    fun storeBackers(backers: List<User>) {
        for (backer in backers) {
            backerListArray.add(backer)
        }
        Log.d("yay", backerListArray.first().email.toString())
    }

    fun storeTransactions() {
        transactionListArray.add(defaultTransaction)
    }
}