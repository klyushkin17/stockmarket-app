package com.plcoding.stockmarketapp.data.remote.dto

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query


interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String
    ): ResponseBody

    companion object {
        const val API_KEY = "8BHKQX4RFU4CG6IY"
        const val BASE_URl = "https://alphavantage.co"
    }

}