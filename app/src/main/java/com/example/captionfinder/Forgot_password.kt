package com.example.captionfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class Forgot_password : AppCompatActivity() {

    private var mAuth: FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        mAuth=FirebaseAuth.getInstance()
        send_forgot.setOnClickListener {
            forgot()
        }
    }

    private fun forgot() {
        var mail=mail_forgot.text.toString()
        if(TextUtils.isEmpty(mail))
            Toast.makeText(this,"Enter Mail",Toast.LENGTH_SHORT).show()
        else{
        mAuth!!.sendPasswordResetEmail(mail).addOnCompleteListener(this){task->
            if(task.isSuccessful) {
                Toast.makeText(this,"Email sent Successfull",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,Login::class.java))
            }
            else{
                Toast.makeText(this,"No mail registered",Toast.LENGTH_SHORT).show()
            }
        }

        }

    }
}
