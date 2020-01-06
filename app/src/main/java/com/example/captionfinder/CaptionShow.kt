package com.example.captionfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        for (i in 0..newString.size-1){
            str+= (i+1).toString() +". "+newString[i]+"\n"
        }
show.text=str
    }

}
