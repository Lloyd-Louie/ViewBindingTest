package com.example.multilanguage.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class MemeImages(
    @SerializedName("meme_list") val memeList: List<MemeItem>? = null,
)

@Keep
data class ErrorResponse(
    @SerializedName("title") val title: String? = null,
    @SerializedName("details") val details: String? = null,
    @SerializedName("http_code") val httpCode: Int? = null,
) : Serializable


@Keep
data class MemeItem(
    @SerializedName("link") val link: String? = null,
    @SerializedName("name") val name: String? = null,
    ) : Serializable