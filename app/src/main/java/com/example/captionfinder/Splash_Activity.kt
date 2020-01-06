package com.example.captionfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.os.postDelayed

class Splash_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_)

        Handler().postDelayed({
            startActivity(Intent(this,Login_regsiter_both::class.java))
            finish()

        },3000)
    }
}
