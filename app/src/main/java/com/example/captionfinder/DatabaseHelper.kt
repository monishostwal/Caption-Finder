package com.example.captionfinder

import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class DatabaseHelper(var ctx: Context) :
    SQLiteOpenHelper(
        ctx,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {
    fun find_list(list:ArrayList<String>): ArrayList<ArrayList<String>> {


            var modified_list=modify_list(list)
            var list_of_list=ArrayList<ArrayList<String>>()
        for (item in modified_list)
        {
            var contList: ArrayList<String> = ArrayList()
            var db = this.readableDatabase
            val cursor: Cursor? = db.rawQuery("SELECT captions FROM tag WHERE tag=?", arrayOf(item))
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val cont =
                        (cursor.getString(0))
                    contList.add(cont)
                }
                cursor.close()
                db.close()

            }
            list_of_list.add(contList)

        }

            return list_of_list
        }

    private fun modify_list(list: ArrayList<String>): ArrayList<String> {
        var map:HashMap<String,String> = hashMapOf("Animal" to "animal","Pet" to "animal","Cat" to "cat","Dog" to "dog","Hair" to "hair","Eyelash" to "eyes","Smile" to "smile","Skin" to "skin","Hat" to "hat","Community" to "group","Mouth" to "skin","Interaction" to "group","Crowd" to "group","Event" to "music","Concert" to "music","Jeans" to "fashion","Shirt" to "fashion","Sari" to "fashion","Denim" to "fashion","Pocket" to "fashion",
            "Nature" to "nature","Sky" to "nature","Mountain" to "mountain","Winter" to "winter","Ice" to "winter","Plant" to "nature","Flower" to "flower","Plant" to "flower","Petal" to "flower","Fun" to "fun","Leisure" to "fun","Car" to "car","Vehicle" to "car","Monument" to "monument","Building" to "monument","Statue" to "monument","Food" to "food","Icing" to "food","cool" to "winter","Vacation" to "vacation","Muscle" to "muscle","Swimming" to "swimming","Garden" to "flower","Fire" to "winter","Christmas" to "winter",
            "Selfie" to "selfie","Glasses" to "glasses")
        var new_list=ArrayList<String>()
        new_list.add("general")
        for (item in list) {
            if(map.contains(item)){
                    if( map.getValue(item) !in new_list){
            var new_item = map.getValue(item)
            new_list.add(new_item)}}
        }
        for (i in new_list)
        Log.d("new_list",i)
        return new_list
    }

    @Throws(IOException::class)
    fun CopyDataBaseFromAsset() {
        val myInput =
            ctx.assets.open(DATABASE_NAME)
        // Path to the just created empty db
        val outFileName = getDatabasePath()
        // if the path doesn't exist first, create it
        val f =
            File(ctx.applicationInfo.dataDir + DB_PATH_SUFFIX)
        if (!f.exists()) f.mkdir()
        // Open the empty db as the output stream
        val myOutput: OutputStream = FileOutputStream(outFileName)
        // transfer bytes from the inputfile to the outputfile
        val buffer = ByteArray(1024)
        var length: Int
        while (myInput.read(buffer).also { length = it } > 0) {
            myOutput.write(buffer, 0, length)
        }
        // Close the streams
        myOutput.flush()
        myOutput.close()
        myInput.close()
    }

    @Throws(SQLException::class)
    fun openDataBase(): SQLiteDatabase {
        val dbFile =
            ctx.getDatabasePath(DATABASE_NAME)
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset()
                println("Copying sucess from Assets folder")
            } catch (e: IOException) {
                throw RuntimeException("Error creating source database", e)
            }
        }
        return SQLiteDatabase.openDatabase(
            dbFile.path,
            null,
            SQLiteDatabase.NO_LOCALIZED_COLLATORS or SQLiteDatabase.CREATE_IF_NECESSARY
        )
    }

    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
    }
    private fun getDatabasePath(): String? {
        return (ctx.applicationInfo.dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME)
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "tag.db"
        private const val DB_PATH_SUFFIX = "/databases/"

    }

}