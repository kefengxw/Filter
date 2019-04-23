package com.filter.android.model.remote

import com.google.gson.annotations.SerializedName

data class RemoteToken(
    @SerializedName("access_token")
    val accessToken: String = "",
    @SerializedName("token_type")
    val tokenType: String = "",
    @SerializedName("expires_in")
    val expiresIn: Int = 0
)