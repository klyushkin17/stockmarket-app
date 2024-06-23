package com.plcoding.stockmarketapp.di

import com.plcoding.stockmarketapp.data.csv.CSVParser
import com.plcoding.stockmarketapp.data.csv.CompanyListingsParser
import com.plcoding.stockmarketapp.data.csv.IntradayInfoParser
import com.plcoding.stockmarketapp.data.repository.StockRepositoryImpl
import com.plcoding.stockmarketapp.domain.model.CompanyInfo
import com.plcoding.stockmarketapp.domain.model.CompanyListing
import com.plcoding.stockmarketapp.domain.model.IntradayInfo
import com.plcoding.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    //Зависимость для companyListringsParser
    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        //Для того, чтобы внедрить нужный тип daggerHilt должен исользовать данную имплементацию
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyListing>//Тип, который должен уметь вндерять daggerHilt

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfo>

    //Для того, чтобы внедрить repository в ViewModel используется следующая функция
    @Binds
    @Singleton
    abstract fun bindStockRepository(
        //Чтобы внедрить нужный тип, dagger должен использовать данную имплементацию
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository //Мы научить dagger внедрять данный тип
}