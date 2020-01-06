package com.example.captionfinder


import android.util.Log
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class Caption_Generator{
    fun findCaption(list:ArrayList<ArrayList<String>>):ArrayList<String>{
        var list_of_captions=ArrayList<String>()
        var size_of_list=list.size
        var caption_of_each_category=20/size_of_list
        for( i in list){
           var num_list=ArrayList<Int>()
            var j=0
            while(j !=caption_of_each_category){
                var temp= Random.nextInt(0,i.size)
                if(temp !in num_list){
                    num_list.add(temp)
                    list_of_captions.add(i[temp])
                    j+=1
                }

            }

    }

        return list_of_captions
    }
}
