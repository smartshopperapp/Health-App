package com.example.myapplication1

import android.content.ContentValues.TAG
import android.content.DialogInterface
import com.google.firebase.auth.FirebaseAuth


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication1.databinding.ActivityRegistrationBinding
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {

    val ref = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "HEALTH GUIDE APP"
        val button = findViewById<Button>(R.id.new_account)
        button.setOnClickListener {
            val intent = Intent(this, registration::class.java)
            startActivity(intent)

        }
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val login_button = findViewById<Button>(R.id.login_home)
        val forgot_button = findViewById<Button>(R.id.rest_pwd)
        lateinit var binding: ActivityRegistrationBinding
        var flag: Boolean
        flag = true





            //validating EMAIL
            if (TextUtils.isEmpty(email.toString())) {
                flag = false
                binding.regEmail.error = "Please Enter Email"
                Toast.makeText(this, "Plese enter email", Toast.LENGTH_SHORT).show()

            }

            //validate password
            if (TextUtils.isEmpty(password.toString())) {
                flag = false
                binding.regPassword.error = "Please enter password"
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
            }

            println("\n\n**************$flag***************\n\n")
            println("\n\nPavata reeeeee veta reeeee**************\n\n")






                //VERIFYING USER IN DATABASE
                login_button.setOnClickListener {
                    if (!flag) {
                        println("Empty string************************************ ")
                    }
                    else {
                        println("Else'an veta reeeee**************")


                        ref.signInWithEmailAndPassword(
                            email.text.toString().trim(),
                            password.text.toString().trim()
                        )
                            .addOnSuccessListener {

                                Toast.makeText(this, "Successfully Signed IN", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Failed To Sign IN", Toast.LENGTH_SHORT).show()

                            }
                    }
                }

        forgot_button.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            builder.setTitle("Please Enter EMAIL")
            val view = layoutInflater.inflate(R.layout.diolog_forgot_password, null)
            val user_name = view.findViewById<EditText>(R.id.et_user_name)
            val value = user_name.toString()
            println("the value of  $value")
            builder.setView(view)

            fun forgotPassword(user_name: EditText) {
                if (user_name.text.toString().isEmpty()) {
                    return
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(user_name.text.toString()).matches()) {
                    return
                }

                ref.sendPasswordResetEmail(user_name.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Email Sent", Toast.LENGTH_SHORT).show()
                        }

                    }
            }

            builder.setPositiveButton("RESET", DialogInterface.OnClickListener { _, _ ->
                forgotPassword(user_name)

            })
            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _, _ -> })
            builder.show()
        }





    }
}

/*
println("\n\n****** ${ password.text.toString().trim()} ********\n\n")
                println("\n" + "\n" + "****** ${email.text.toString().trim()} ********\n" + "\n")
                println("\n\n******* $flag ********\n\n")
 */



