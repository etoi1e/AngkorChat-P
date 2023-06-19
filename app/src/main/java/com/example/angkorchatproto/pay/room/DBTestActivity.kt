package com.example.angkorchatproto.pay.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.angkorchatproto.R
import com.facebook.stetho.Stetho

class DBTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dbtest)

        Stetho.initializeWithDefaults(this);

        val db = AppDatabase.getInstance(applicationContext)

        if(db != null){
            //val newAccount = AccountInfo("123d03d11Zxz112","userID","0000","ABA",1000,100)
            db.paymentDao().delUserAccount("userID")
            //finish()
        }



    }
}