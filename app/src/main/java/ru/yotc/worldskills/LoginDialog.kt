package ru.yotc.worldskills

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class LoginDialog(private val callback: (login: String, password: String)->Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val loginLayout = layoutInflater.inflate(R.layout.auth, null)

            val loginText = loginLayout.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.login)
            val loginError = loginLayout.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.login_error)
            val passwordText = loginLayout.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.password)
            val passwordError = loginLayout.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.password_error)
            val loginButton = loginLayout.findViewById<TextView>(R.id.logButton)

            val myDialog = builder.setView(loginLayout)
                    .setTitle("Авторизация!")
                    .create()

            loginButton.setOnClickListener {
                var hasErrors = false
                if(loginText.text.isNullOrEmpty()){
                    hasErrors = true
                    loginError.error = "Поле должно быть заполнено"
                }
                else
                    loginError.error = ""

                if(passwordText.text.isNullOrEmpty()){
                    hasErrors = true
                    passwordError.error = "Поле должно быть заполнено"
                } else
                    passwordError.error = ""

                if(!hasErrors) {
                    myDialog.dismiss()
                    callback.invoke(
                            loginText.text.toString(),
                            passwordText.text.toString()
                    )
                }
            }

            myDialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}