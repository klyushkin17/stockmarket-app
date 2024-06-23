package com.plcoding.stockmarketapp.presentation.company_listings_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plcoding.stockmarketapp.domain.repository.StockRepository
import com.plcoding.stockmarketapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.ws.RealWebSocket
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    private val repository: StockRepository
): ViewModel(){
    //Получаем состояние из класса CompanyListringsState
    var state by mutableStateOf(CompanyListingsState())

    private  var searchJob: Job? = null

    //Получаем список компаний при первоначальной загрузке экрана
    init {
        getCompanyListings()
    }

    //Функция onEvent, которая вызывается из .kt файла и проверяет, какой Event из класса
    //CompanyListringsEvent был вызван и описывает действия при том или ином действии на экране
    fun onEvent(event: CompanyListingsEvent) {
        when(event) {
            is CompanyListingsEvent.Refresh -> {
                getCompanyListings(fetchFromRemote = true)
            }
            is CompanyListingsEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }
        }
    }

    //Функция получения списка компаний
    private fun getCompanyListings(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        //Создаем корутину в viewModelScope
        viewModelScope.launch {
            repository
                //Вызываем фунцию из repository
                .getCompanyListings(fetchFromRemote, query)
                //Собираем данные из Flow
                .collect {result ->
                    //Проверяем полученный результат и обрабатываем в зависимости от него
                    when(result) {
                        is Resource.Success -> {
                            //Изменяем значения companies из State
                            result.data?.let {listings ->
                                state = state.copy(
                                    companies = listings
                                )
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            //Изменяем значение isLoading из State
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }
}