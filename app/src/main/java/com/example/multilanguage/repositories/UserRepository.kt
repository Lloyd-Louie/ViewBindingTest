package com.example.multilanguage.repositories

import com.example.multilanguage.MultiLanguageApplication
import com.example.multilanguage.api.ApiService
import com.example.multilanguage.model.ErrorResponse
import com.example.multilanguage.model.MemeImages
import com.haroldadmin.cnradapter.NetworkResponse

interface UserRepository {
     suspend fun getMemeImages() : NetworkResponse<MemeImages, ErrorResponse>
}

class UserRepositoryImpl(
     private val apiService: ApiService
) : UserRepository{
     override suspend fun getMemeImages(): NetworkResponse<MemeImages, ErrorResponse> {
          return apiService.getMemeImages()
     }
}

