package com.beni.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ExampleResponse(
    @field:SerializedName("response_field1")
    val field1: String,

    @field:SerializedName("response_field2")
    val field2: String,
)
