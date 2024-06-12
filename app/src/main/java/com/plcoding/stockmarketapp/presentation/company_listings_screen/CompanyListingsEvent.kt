package com.plcoding.stockmarketapp.presentation.company_listings_screen

sealed class CompanyListingsEvent {
    object Refresh: CompanyListingsEvent()
    data class OnSearchQueryChange(val query: String): CompanyListingsEvent()
}