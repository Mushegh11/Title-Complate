package com.mushegh.myapplication.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
     companion object {
        private val BASE_URL : String = "https://jsonplaceholder.typicode.com"
         private var retrofit : Retrofit? = null
         public fun getClient() : Retrofit
         {
             if(retrofit ==null)
             {
                 retrofit = Retrofit.Builder()
                     .baseUrl(BASE_URL)
                     .addConverterFactory(GsonConverterFactory.create())
                     .build()
             }
             return retrofit!!
         }
    }

}