package com.example.bankdata.repository

import com.example.bankdata.service.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllBankData() = retrofitService.getData()
}