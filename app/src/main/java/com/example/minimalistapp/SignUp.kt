package com.example.minimalistapp


import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.vishnusivadas.advanced_httpurlconnection.PutData


class SignUp : AppCompatActivity() {


    private lateinit var textInputEditTextFullname: TextInputEditText
    private lateinit var textInputEditTextUsername: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var buttonSignUp: Button
    private lateinit var textViewLogin: TextView
    private lateinit var progressBar: ProgressBar


    // Cambia esta URL por la dirección de tu servidor XAMPP
    private val signUpUrl = "https://dqasbaut.lucusprueba.es/insertar.php"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        textInputEditTextFullname = findViewById(R.id.fullname)
        textInputEditTextUsername = findViewById(R.id.username)
        textInputEditTextPassword = findViewById(R.id.password)
        textInputEditTextEmail = findViewById(R.id.email)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        textViewLogin = findViewById(R.id.loginText)
        progressBar = findViewById(R.id.progress)


        textViewLogin.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }


        buttonSignUp.setOnClickListener {
            val fullname = textInputEditTextFullname.text.toString()
            val username = textInputEditTextUsername.text.toString()
            val password = textInputEditTextPassword.text.toString()
            val email = textInputEditTextEmail.text.toString()


            if (fullname.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {
                progressBar.visibility = View.VISIBLE
                SignUpTask().execute(fullname, username, password, email)
            } else {
                Toast.makeText(applicationContext, "All fields required", Toast.LENGTH_SHORT).show()
            }
        }


        // Agregar un botón para ir a MainActivity
        val buttonGoToMain: Button = findViewById(R.id.buttonGoToMain)
        buttonGoToMain.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }


    inner class SignUpTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String): String {
            val fullname = params[0]
            val username = params[1]
            val password = params[2]
            val email = params[3]


            val field = arrayOf("fullname", "username", "password", "email")
            val data = arrayOf(fullname, username, password, email)
            val putData = PutData(signUpUrl, "POST", field, data)
            if (putData.startPut() && putData.onComplete()) {
                return putData.getResult()
            }
            return "Error occurred"
        }


        override fun onPostExecute(result: String) {
            progressBar.visibility = View.GONE
            if (result == "Sign Up Success") {
                Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, Login::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
