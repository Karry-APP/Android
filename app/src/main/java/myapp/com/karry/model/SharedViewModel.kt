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


    val transactionId = MutableLiveData<String>()
    private val defaultSharedImageList: ArrayList<SharedImage> = arrayListOf()

    private val defaultAuthor: User = User("toto", "Nicolas", "Leroy", "0646862158", "nico@kkarry.fr", "https://png.pngtree.com/element_origin_min_pic/17/09/18/d555144313d6d69a8820a3baaf5d81fe.jpg")
    private val defaultTransaction = Transaction("test", "Hahahahhahahahha", "Huilde d'olive", "24, 30 €", defaultSharedImageList, defaultAuthor)
    val tripListArray: ArrayList<Trip> = arrayListOf()
    val transactionListArray: ArrayList<Transaction> = arrayListOf()
    val backerListArray: ArrayList<User> = arrayListOf()


    val orderPayload = CreateOrderPayload()


    fun fillCreateOrderFormPayload(): JSONObject {
        val userObject= JSONObject()
        userObject.put("departure",departureValue.value)
        userObject.put("destination",destinationValue.value)
        userObject.put("carryWeight",carryWeight.value)
        userObject.put("carryVolume",carryVolume.value)
        userObject.put("carryTax",carryTax.value)

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

    fun storeTransactions() {
        transactionListArray.add(defaultTransaction)
    }
}