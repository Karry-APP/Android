package myapp.com.karry.modules

import android.util.Log
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import myapp.com.karry.R


class EditTextChecker(private val editText: EditText) {

    private var isRequired: Boolean = false
    private var minLengthValue: Int = 0
    private var maxLengthValue: Int = 10

    private lateinit var isRequiredErrorMessage: String
    private lateinit var minLengthErrorMessage: String
    private lateinit var maxLengthErrorMessage: String

    fun isRequired(errorMessage: String = "Ce champs est obligatoire"): EditTextChecker {
        this.isRequired = true
        this.isRequiredErrorMessage = errorMessage
        return this
    }

    fun minLength(minLengthValue: Int, errorMessage: String = "Erreur (trop court)"): EditTextChecker {
        this.minLengthValue = minLengthValue
        this.minLengthErrorMessage = errorMessage
        return this
    }

    fun maxLength(maxLengthValue: Int, errorMessage: String = "Erreur (trop long)"): EditTextChecker {
        this.maxLengthValue = maxLengthValue
        this.maxLengthErrorMessage = errorMessage
        return this
    }

    private fun extractString(): String {
        return editText.text.toString()
    }

    fun check(valid: (value: String) -> Unit): Boolean {

        val value = this.extractString()

        // IS REQUIRED
        if(value.isEmpty() || value.isBlank()) {
            this.editText.error = this.isRequiredErrorMessage
            this.editText.setBackgroundResource(R.drawable.bg_input_error)
            return false
        }

        // MIN LENGTH
        if(value.length < minLengthValue) {
            this.editText.error = this.minLengthErrorMessage
            this.editText.setBackgroundResource(R.drawable.bg_input_error)
            return false
        }

        // MAX LENGTH
        if(value.length > maxLengthValue) {
            this.editText.error = this.maxLengthErrorMessage
            this.editText.setBackgroundResource(R.drawable.bg_input_error)
            return false
        }

        this.editText.setBackgroundResource(R.drawable.bg_input)
        valid(value)
        return true
    }
}


class SpinnerChecker(private val spinner: Spinner) {

    companion object {
        val itemsDeparture = arrayOf("Choisissez une ville de départ", "Paris", "Berlin", "Tokyo", "Lille", "Nantes", "Bordeaux")
        val itemsDestination = arrayOf("Choisissez une ville de destination", "Paris", "Berlin", "Tokyo", "Lille", "Nantes", "Bordeaux")
    }

    private var isRequired: Boolean = false

    private lateinit var notEqualToValue: String
    private lateinit var isRequiredErrorMessage: String

    fun isRequired(errorMessage: String = "Ce champs est obligatoire"): SpinnerChecker {
        this.isRequired = true
        this.isRequiredErrorMessage = errorMessage
        return this
    }

    fun notEqualTo(defaultValue: String): SpinnerChecker {
        this.notEqualToValue = defaultValue
        return this
    }

    private fun extractString(): String {
        return spinner.selectedItem.toString()
    }

    fun check(valid: (value: String) -> Unit): Boolean {

        val value = this.extractString()

        // IS REQUIRED
        if(value === notEqualToValue) {
            this.spinner.setBackgroundResource(R.drawable.bg_input_error)
            return false
        }

        this.spinner.setBackgroundResource(R.drawable.bg_input)
        valid(value)
        return true
    }
}
