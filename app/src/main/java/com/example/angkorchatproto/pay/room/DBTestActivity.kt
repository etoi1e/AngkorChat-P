package com.example.angkorchatproto.pay.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.angkorchatproto.R


class DBTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dbtest)

        val db = AppDatabase.getInstance(applicationContext)

        if(db != null){

//            val newAccount = AccountInfo("123456","0000","1000","0000",1000,1000)
//            val newTransfer = TransferInfo(0,newAccount.accountNumber,"0000",1000,"0000","Shopping","time")
//            db.paymentDao().insertAccount(newAccount)
//            db.paymentDao().insertTransfer(newTransfer)
//            finish()

        }



    }
}