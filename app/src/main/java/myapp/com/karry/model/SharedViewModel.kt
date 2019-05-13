package myapp.com.karry.model
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import myapp.com.karry.entity.*

class SharedViewModel : ViewModel() {
    val destinationValue = MutableLiveData<String>()
    val arrivalValue = MutableLiveData<String>()
    val transactionId = MutableLiveData<String>()
    private val defaultSharedImageList: ArrayList<SharedImage> = arrayListOf()

    private val defaultAuthor: User = User("toto", "Nicolas", "Leroy", "0646862158", "nico@kkarry.fr", "https://png.pngtree.com/element_origin_min_pic/17/09/18/d555144313d6d69a8820a3baaf5d81fe.jpg")
    private val defaultTransaction = Transaction("test", "Hahahahhahahahha", "Huilde d'olive", "24, 30 €", defaultSharedImageList, defaultAuthor)
    private val defaultTrip = Trip("test", "totototototootot", "Paris", "Londres", "France", "England", "6", "9", "4,20 €", "Toto")
    private val defaultBacker = Backer("Nico", "toto", "toto", "https://png.pngtree.com/element_origin_min_pic/17/09/18/d555144313d6d69a8820a3baaf5d81fe.jpg")
    val tripListArray: ArrayList<Trip> = arrayListOf()
    val transactionListArray: ArrayList<Transaction> = arrayListOf()
    val backerListArray: ArrayList<Backer> = arrayListOf()

    fun setTransactionId(id: String) {
        transactionId.value = id
    }

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
        tripListArray.add(defaultTrip)
    }

    fun storeBackers() {
        backerListArray.add(defaultBacker)
    }

    fun storeTransactions() {
        transactionListArray.add(defaultTransaction)
    }
}