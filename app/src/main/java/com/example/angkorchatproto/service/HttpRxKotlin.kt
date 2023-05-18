package com.example.angkorchatproto.service

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object HttpRxKotlin {
    @SuppressLint("CheckResult")
    fun sendFCMNotification(token: String, data: Data.VideoCallInfo, param: Observer<Data.HttpResponseBase>) {
        val gson = Gson()
        val jsonStr = gson.toJson(Data.SendData(token, data))
        Log.d("jongchan.won","fcm 보낼데이터 : $jsonStr")
        HttpConnection.createService(HttpRequestData::class.java)
            .sendFCMNotification("key=AAAAqietA54:APA91bHtbdDZ6eSUFVRQC2HgWRc_5M5q1lEKj5OLI6Ci2SwAZ6j2nqhkJXXCbY3uknGycIfSvCoApj_VQdXXyBa89H-rm76LNygO8CRK19hfgeXXV1baBnH9nowSuGM1vL54fl1a1X3R", Data.SendData(token, data))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.d("jongchan.won","fcm 보내기 에러: $it")
            }
            .doOnNext {
                Log.d("jongchan.won","${it}")
            }
            .subscribe(param)
    }
}

