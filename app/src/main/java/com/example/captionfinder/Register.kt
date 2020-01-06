package com.example.captionfinder

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.button4

class Register : AppCompatActivity() {
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    private var mProgressBar: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        button4.setOnClickListener {
            startActivity(Intent(this,Login::class.java))
        }
        mProgressBar=ProgressDialog(this)
        mAuth= FirebaseAuth.getInstance()
        mDatabase= FirebaseDatabase.getInstance()
        mDatabaseReference=mDatabase!!.reference.child("")
        signup_reg.setOnClickListener {
            registeruser()
        }
    }

    private fun registeruser() {
        var email_str=mail_reg.text.toString()
        var password_str=password_reg.text.toString()
        var name=name.text.toString()
        if (TextUtils.isEmpty(email_str)|| TextUtils.isEmpty(password_str)|| TextUtils.isEmpty(name))
            Toast.makeText(this,"Enter All Fields!",Toast.LENGTH_SHORT).show()
        else if (password_str.length<6)
            Toast.makeText(this,"Minimum password length is 6",Toast.LENGTH_SHORT)
        else{
            mProgressBar!!.setMessage("Registering User")
            mProgressBar!!.show()
            mAuth!!.createUserWithEmailAndPassword(email_str,password_str).addOnCompleteListener(this){task->
                mProgressBar!!.hide()
                if(task.isSuccessful){
                    val userid=mAuth!!.currentUser!!.uid
                    val muser=mAuth!!.currentUser
                    muser!!.sendEmailVerification()
                    Toast.makeText(this,"Verification Mail Sent to "+email_str,Toast.LENGTH_SHORT).show()
                    val currentuser=mDatabaseReference!!.child(userid)
                    currentuser.child("Email").setValue(email_str)
                    currentuser.child("Name").setValue(name)
                   startActivity(Intent(this,Login::class.java))
                }
                else
                    Toast.makeText(this,"Authentication Failed",Toast.LENGTH_SHORT).show()

            }
        }

    }

    }
