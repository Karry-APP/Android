package myapp.com.karry.modules

import android.widget.EditText

class FormChecker {

    companion object {
        fun isValidDescription(editText: EditText, description: String): Boolean {
            if(description.isBlank()) {
                editText.error = "Vous avez oubliez de rentrer la description :/"
                return false
            }

            if(description.length < 15) {
                editText.error = "Votre description est trop courte"
                return false
            }

            if(description.length >= 100) {
                editText.error = "Votre description est trop longue"
                return false
            }
            return true
        }
    }
}