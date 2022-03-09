package com.emamagic.entity

import com.google.gson.annotations.SerializedName

data class PhoneNumber(
    @SerializedName("phone_number")
    val phoneNumber: String
)