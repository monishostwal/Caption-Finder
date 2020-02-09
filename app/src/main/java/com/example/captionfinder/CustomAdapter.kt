package com.example.captionfinder

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Build
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextClock
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.*


abstract class CustomAdapter: RecyclerView.Adapter<CustomAdapter.ViewHolder> {


    var context:Context?=null
    var items:List<String>
    lateinit var tts:TextToSpeech
    lateinit var text_to_speak:String


    constructor(context: Context, items:List<String>){
        this.context=context
        this.items=items

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        var item= LayoutInflater.from(context).inflate(R.layout.activity_list_item,parent,false)
        tts= TextToSpeech(context,TextToSpeech.OnInitListener { status ->
            if(status!=TextToSpeech.ERROR){
                tts.language=Locale.UK
            }
        })
        return (ViewHolder(item))

    }

    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        var new_caption=items.get(position)
        holder.caption_single.text=new_caption
        holder.copy_button.setOnClickListener {
            setClipboard(context!!, holder.caption_single.text as String)
            Toast.makeText(context,"Copied!!",Toast.LENGTH_SHORT).show()

        }

        holder.favorite_button.setOnClickListener {
            var ld=LocalDatabase(context!!)
            ld.adddata(holder.caption_single.text as String)
            Toast.makeText(context,"Added to favourites",Toast.LENGTH_SHORT).show()

        }
        holder.listen_button.setOnClickListener {
            tts.speak(holder.caption_single.text as String,TextToSpeech.QUEUE_FLUSH,null)
        }

    }


    override fun getItemCount(): Int {
        return items.size
    }


    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val caption_single = itemView.findViewById(R.id.title) as TextView
        val copy_button=itemView.findViewById(R.id.copy_button) as Button
        val favorite_button=itemView.findViewById(R.id.favorite_button) as Button
        val listen_button=itemView.findViewById(R.id.listen_button) as Button
    }

    private  fun setClipboard(
        context: Context,
        text: String
    ) {

            val clipboard =
                context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", text)
            clipboard.primaryClip = clip
        }
    }
