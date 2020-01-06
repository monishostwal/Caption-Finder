package com.example.captionfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login_regsiter_both.*

class Login_regsiter_both : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_regsiter_both)
        button3.setOnClickListener {
            startActivity(Intent(this,Register::class.java))
        }
        button4.setOnClickListener {
            startActivity(Intent(this,Login::class.java))
        }
    }
}
