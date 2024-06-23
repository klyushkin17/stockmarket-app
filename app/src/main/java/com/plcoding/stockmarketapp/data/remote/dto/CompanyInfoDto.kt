package com.plcoding.stockmarketapp.data.remote.dto

import com.squareup.moshi.Json

data class CompanyInfoDto(
    //Для того, чтобы пометить несовпадение имен в json-файле и в dto применяется конструкция
    //@field:Json(name = "jsonFieldName")
    @field:Json(name = "Symbol") val symbol: String?,
    @field:Json(name = "Description") val description: String?,
    @field:Json(name = "Name") val name: String?,
    @field:Json(name = "Country") val country: String?,
    @field:Json(name = "Industry") val industry: String?,
)