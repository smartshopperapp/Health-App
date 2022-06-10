package com.example.myapplication1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.example.myapplication1.databinding.ActivityMainBinding
import com.example.myapplication1.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.util.Log
import android.util.Patterns
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import kotlin.reflect.typeOf

class registration : AppCompatActivity() {

    private lateinit var binding : ActivityRegistrationBinding
    val ref = FirebaseAuth.getInstance()
    private lateinit var user_details_db : DatabaseReference
    private lateinit var disease_db : DatabaseReference
    var radioGroup: RadioGroup? = null
    lateinit var radio_group_value: RadioButton
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "HEALTH GUIDE APP"
        radioGroup = findViewById(R.id.radio_gender)

        binding.reg.setOnClickListener {

            val intSelectButton: Int = radioGroup!!.checkedRadioButtonId
            radio_group_value = findViewById(intSelectButton)
            val name = binding.regName.text.toString()
            val password = binding.regPassword.text.toString()
            val re_password = binding.regRepassword.text.toString()
            val address = binding.regAddress.text.toString()
            val age = binding.regAge.text.toString()
            val email = binding.regEmail.text.toString()
            val gender = radio_group_value.text.toString()
            val userid = binding.userid.text.toString()
            val disease_list = mutableListOf<Boolean>()
            val val_flag: Boolean
            println("value re.............******$disease_list")
            println("GENDER CHO VALUE********$gender")


            fun validate ():Boolean {
                var flag: Boolean = true

                //validating USERID
                if (TextUtils.isEmpty(userid)) {
                    flag = false
                    binding.userid.error = "Please Enter Userid"
                    Toast.makeText(this, "Enter UserId", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Please Enter UserId", Toast.LENGTH_SHORT).show()
                }

                //validating NAME
                if (TextUtils.isEmpty(name)) {
                    flag = false
                    binding.regName.error = "Please Enter Name"
                    Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show()
                }

                //validating AGE
                if (TextUtils.isEmpty(age)) {
                    flag = false
                    binding.regAge.error = "Please Enter Age"
                    Toast.makeText(this, "Please Enter Age", Toast.LENGTH_SHORT).show()
                }

               //validating ADDRESS
                if (TextUtils.isEmpty(address)) {
                    flag = false
                    binding.regAddress.error = "Please Enter Address"
                    Toast.makeText(this, "Please Enter Address", Toast.LENGTH_SHORT).show()
                }

                //validating EMAIL
                if (TextUtils.isEmpty(email)) {
                    flag = false
                    binding.regEmail.error = "Please Enter Email"
                    Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show()
                }

                //validate mail
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    flag = false
                    binding.regEmail.error = "Invalid email format"
                    Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show()

                }

                //validate password
                if (TextUtils.isEmpty(password)) {
                    flag = false
                    binding.regPassword.error = "Please enter password"
                    Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
                }
                //password length less than 6
                if (password.length < 6) {
                    flag = false
                    binding.regPassword.error = "Enter minimum 6 characters"
                    Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()
                }
                //validate repassword
                if (TextUtils.isEmpty(re_password)) {
                    flag = false
                    binding.regRepassword.error = "Please enter repassword"
                    Toast.makeText(this, "Please Enter RePassword", Toast.LENGTH_SHORT).show()
                }

               //repassword and password check
                if (re_password != password) {
                    flag = false
                    binding.regRepassword.error = "repassword not matched"
                }

                //validate
                if(gender == "")
                {
                    flag = false
                    Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show()
                }

            return flag
        }

            //ASSINGING VALUES TO CHECKBOX
            if(findViewById<CheckBox>(R.id.check_cholestrol).isChecked)
                disease_list.add(0,true)
            else
                disease_list.add(0,false)

            if(findViewById<CheckBox>(R.id.check_diabetes).isChecked)
                disease_list.add(1,true)
            else
                disease_list.add(1,false)

            if(findViewById<CheckBox>(R.id.check_jaundice).isChecked)
                disease_list.add(2,true)
            else
                disease_list.add(2,false)

            //CREATING OBJECT
            val dis = disease(disease_list[0],disease_list[1],disease_list[2])
            user_details_db = FirebaseDatabase.getInstance().getReference("user")
            val user = user(name,password,re_password,address,age,email,gender)
            user_details_db = user_details_db.child(userid)

            //CLEARING TXT FIELDS
            if(validate()) {
                    println("\n working succesfully \n")
                    user_details_db.setValue(user).addOnSuccessListener { binding.regName.text.clear()
                        binding.regPassword.text.clear()
                        binding.regRepassword.text.clear()
                        binding.regAddress.text.clear()
                        binding.regAge.text.clear()
                        binding.regEmail.text.clear()
                        binding.userid.text.clear()
                        Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
                    }
                disease_db = user_details_db.child("Disease")
                disease_db.setValue(dis)

                    .addOnFailureListener {
                        Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
                    }
                //CREATING USER IN AUTHENTICATION OF FIREBASE
                ref.createUserWithEmailAndPassword(
                    email.toString().trim(),
                    password.toString().trim()
                )
                    .addOnFailureListener {
                        Toast.makeText(this,"Failed to register in authentication",Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }
}







