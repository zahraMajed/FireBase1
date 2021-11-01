package com.example.firebase1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    var TAG="iAMMainActivity"
    lateinit var btnretreive: Button
    lateinit var btnsave: Button
    lateinit var tv: TextView
    lateinit var edname: EditText
    lateinit var edloc: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edname = findViewById(R.id.edName)
        edloc = findViewById(R.id.edLocation)
        btnretreive = findViewById(R.id.button)
        btnsave = findViewById(R.id.button2)
        tv = findViewById(R.id.textView)

        //step 3:Initialize cloud firestore
        val db = Firebase.firestore

        //step 4: Add data
        // Create a new user with a first and last name
        //when i run this code, it is suppose to save these data to firebase file store
        //(but i have to create database for the first time before run the code)
        btnsave.setOnClickListener(){
            if (edname.text.isNotEmpty()&&edloc.text.isNotEmpty()){
                val user = hashMapOf(
                    "name" to edname.text.toString(),
                    "location" to edloc.text.toString()
                )
                // Add a new document with a generated ID
                db.collection("users").add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }

            }else
                Toast.makeText(applicationContext, "please fill missing entries", Toast.LENGTH_SHORT).show()
        }


        //step 5: Retrive data
        btnretreive.setOnClickListener(){
            db.collection("users").get()
                .addOnSuccessListener{ result ->
                    var details = "\n"
                    for ( i in result) {
                        //since we enter the data with hashMapOf we will get them with map
                        i.data.map { (k,v)->
                            details = details + "$k = $v \n\n" }
                    }
                    tv.text=" $details"
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }


    }//end on create()


}