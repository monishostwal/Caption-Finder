package com.example.captionfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_caption_show.*
import java.util.*


class CaptionShow() : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_caption_show)
        val newString: List<String>
        newString = if (savedInstanceState == null) {
            val extras = intent.extras
            extras?.getStringArrayList("list_of_captions")!!
        }
        else {
            savedInstanceState.getSerializable("list_of_captions") as List<String>
        }
        var str =""
        Collections.shuffle(newString)
        var customadapter=object :CustomAdapter(this,newString){}
        val mLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(applicationContext)
        recyclerview.setLayoutManager(mLayoutManager)
        recyclerview.setItemAnimator(DefaultItemAnimator())
        recyclerview.adapter=customadapter


    }

}
