package com.beni.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ServerResponse (
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("message")
    val message: Any?,

    @field:SerializedName("data")
    val sampleResponse: ExampleResponse? = null,

    @field:SerializedName("list_data")
    val listSampleResponse: List<ExampleResponse>? = null,
)