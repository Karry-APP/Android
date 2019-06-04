package myapp.com.karry.model
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_update_profile.*
import myapp.com.karry.entity.*
import myapp.com.karry.modules.UserInfoManager
import org.json.JSONObject

class SharedViewModel : ViewModel() {
    val departureValue = MutableLiveData<String>()
    val destinationValue = MutableLiveData<String>()
    val carryWeight = MutableLiveData<Float>()
    val carryTax= MutableLiveData<Int>()
    val carryVolume= MutableLiveData<Int>()
    val userID = MutableLiveData<String>()
    val roundTrip = MutableLiveData<Boolean>()
    val arrivalDate = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val carryMaxAmount = MutableLiveData<Int>()


    val transactionId = MutableLiveData<String>()
    private val defaultSharedImageList: ArrayList<SharedImage> = arrayListOf()

    private val defaultAuthor: User = User("toto", "Nicolas", "Leroy", "0646862158", "nico@kkarry.fr", "https://png.pngtree.com/element_origin_min_pic/17/09/18/d555144313d6d69a8820a3baaf5d81fe.jpg", "", "Pas de description encore")
    private val defaultTransaction = Transaction("test", "Hahahahhahahahha", "Huilde d'olive", "24, 30 €", defaultSharedImageList, defaultAuthor)
    val tripListArray: ArrayList<Trip> = arrayListOf()
    val transactionListArray: ArrayList<UserRequest> = arrayListOf()
    val backerListArray: ArrayList<User> = arrayListOf()


    val orderPayload = CreateOrderPayload()


    fun fillCreateOrderFormPayload(): JSONObject {
        val userObject= JSONObject()
        userObject.put("departureCity",departureValue.value)
        userObject.put("destinationCity",destinationValue.value)
        userObject.put("carryWeight",carryWeight.value)
        userObject.put("carryVolume",carryVolume.value)
        userObject.put("carryTaxe",carryTax.value)
        userObject.put("owner",userID.value)
        userObject.put("isRound",roundTrip.value)
        userObject.put("arrivalDate",arrivalDate.value)
        userObject.put("carryMaxAmount",carryMaxAmount.value)
        userObject.put("description",description.value)


        return userObject
    }
    
    fun setDestination(item: String) {
        destinationValue.value = item
    }

    fun setDeparture(item: String) {
        departureValue.value = item
    }
  
    fun cleanValues() {
        departureValue.value = ""
        destinationValue.value = ""
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

    fun storeTransactions(transactions: Array<UserRequest>) {
        for (transaction in transactions) {
            transactionListArray.add(transaction)
        }
    }
}