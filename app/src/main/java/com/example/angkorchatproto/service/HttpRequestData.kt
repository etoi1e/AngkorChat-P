package com.example.angkorchatproto.service

import com.example.angkorchatproto.service.Data
import io.reactivex.Observable
import retrofit2.http.*

interface  HttpRequestData {
    @Headers("Content-Type: application/json")
    @POST("send")
    fun sendFCMNotification(@Header("Authorization")token:String,  @Body body: Data.SendData): Observable<Data.HttpResponseBase>
}
