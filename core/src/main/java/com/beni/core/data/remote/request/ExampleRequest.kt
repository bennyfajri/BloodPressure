package com.beni.core.data.remote.request

import com.google.gson.annotations.SerializedName

data class ExampleRequest(
    @field:SerializedName("field1")
    val field1: String,

    @field:SerializedName("field2")
    val field2: String,
)
