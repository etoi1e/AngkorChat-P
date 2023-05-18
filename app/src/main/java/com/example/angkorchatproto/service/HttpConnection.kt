package com.example.angkorchatproto.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HttpConnection {

    /* .baseUrl : 요청할 서버의 기본 URL 을 셋팅하는 메소드 */
    /* .client : okHttp3Client 를 매개변수로 받는 메소드 */
    /* .addCallAdapterFactory : */
    /* .addConverterFactory : 통신 완료 후 어떤 Converter 로 데이터를 파싱할 것인가에 대한 메소드 */
    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl("https://fcm.googleapis.com/fcm/")
        .client(requestHeader)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    private val retrofit: Retrofit = builder.build()

    private val requestHeader: OkHttpClient
        get() {
            return OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build()

        }

    fun <S> createService(
        serviceClass: Class<S>
    ): S {
        return retrofit.create(serviceClass)
    }
}
