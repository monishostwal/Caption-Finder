package com.example.captionfinder

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    private var mProgressBar: ProgressDialog? = null
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mProgressBar= ProgressDialog(this)
        mAuth=FirebaseAuth.getInstance()
        button4.setOnClickListener {
            startActivity(Intent(this,Register::class.java))
        }
        forgot_password.setOnClickListener {
            startActivity(Intent(this,Forgot_password::class.java))
        }
        login.setOnClickListener {
            verifyuser()
        }
    }

    private fun verifyuser() {
        var email=email_login.text.toString()
        var password=password_login.text.toString()
        if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password))
            Toast.makeText(this,"Enter All Fields!!",Toast.LENGTH_SHORT)
        else{
            mProgressBar!!.setMessage("Verifying User")
            mProgressBar!!.show()
            mAuth!!.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){task->
                mProgressBar!!.hide()
                if(task.isSuccessful){
                    startActivity(Intent(this,MainActivity::class.java))

                }
                else{
                    Toast.makeText(this,"Authentication Failed",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}
