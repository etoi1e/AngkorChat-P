package com.example.angkorchatproto.service

import com.google.gson.annotations.SerializedName

interface Data {
    data class SendData(var to: String? = null, var data: VideoCallInfo)
    data class VideoCallInfo(var phoneNumber: String? = null, var token: String? = null)
    data class HttpResponseBase(
        @SerializedName("multicast_id")
        var multicast_id: Int?,
        @SerializedName("success")
        var success: Int?,
        @SerializedName("failure")
        var failure: Int?,
        @SerializedName("canonical_ids")
        var canonical_ids: Int?,
        @SerializedName("results")
        var results: List<Result>?,
    )
    data class Result(
        @SerializedName("message_id")
        var message_id: String
    )
}