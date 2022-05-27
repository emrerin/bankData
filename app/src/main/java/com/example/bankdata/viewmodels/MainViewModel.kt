package com.example.bankdata.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bankdata.model.BDataModel
import com.example.bankdata.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val bDataList = MutableLiveData<List<BDataModel>>()
    var masterList: List<BDataModel> = emptyList()
    val errorMessage = MutableLiveData<String>()
    val progressBar = MutableLiveData<Boolean>()

    fun getAllBankData() {
        val response = repository.getAllBankData()
        response.enqueue(object : Callback<List<BDataModel>> {
            override fun onResponse(call: Call<List<BDataModel>>, response: Response<List<BDataModel>>) {
                if(response.isSuccessful) {
                    masterList = (response.body() as ArrayList<BDataModel>?)!!
                    bDataList.postValue(response.body() as ArrayList<BDataModel>?)
                    progressBar.postValue(true)
                    bDataList.postValue(masterList)
                }
            }

            override fun onFailure(call: Call<List<BDataModel>>, t: Throwable) {
                errorMessage.postValue(t.message)
                progressBar.postValue(false)
            }
        })
    }

    fun filter(query : String)  {
        bDataList.value = masterList.filter {
            it -> it.dc_SEHIR!!.contains(query)
        }
    }
}
