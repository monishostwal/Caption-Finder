package com.example.captionfinder

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val REQUEST_CODE=1
    var bmp:Bitmap?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkpermission()
        snap.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(Intent(intent),REQUEST_CODE)
        }
        detect.setOnClickListener {
            detect_text()
        }

    }

    private fun checkpermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }
    }

    private fun detect_text() {
        var image:FirebaseVisionImage= FirebaseVisionImage.fromBitmap(bmp!!)
        var list=ArrayList<String>()
        val detector=FirebaseVision.getInstance().getOnDeviceImageLabeler()
        detector.processImage(image)
            .addOnSuccessListener { labels ->

                var ans:String=""
                for (label in labels) {
                    val text = label.text
                    val entityId = label.entityId
                    val confidence = label.confidence
                    if (confidence > 0.75) {
                        ans += " "
                        list.add(text)
                        ans += text

                        ans += " "

                    }
                    Log.d("monish_items",ans)



                }

                var dbHelper = DatabaseHelper(this);
                dbHelper.openDataBase();
                var contList = dbHelper.find_list(list);
                var list_of_Captions = Caption_Generator().findCaption(contList)
                var i=Intent(this,CaptionShow::class.java)
                i.putExtra("list_of_captions",list_of_Captions)
                startActivity(i)

            }
            .addOnFailureListener { e ->
                Toast.makeText(this,"Cant find image",Toast.LENGTH_LONG).show()
            }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_CODE && resultCode== Activity.RESULT_OK){
            val stream = contentResolver!!.openInputStream(data!!.getData())
            bmp = BitmapFactory.decodeStream(stream)
            imv.setImageBitmap(bmp)
        }
    }


}
