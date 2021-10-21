package com.example.multilanguage.api

import com.example.multilanguage.model.ErrorResponse
import com.example.multilanguage.model.MemeImages
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.*

interface ApiService {

    @GET("v1/ee0b0cf1-14cc-4d99-bb4d-c932c3fdfa8e")
    suspend fun getMemeImages(

    )
   : NetworkResponse<MemeImages, ErrorResponse>
}