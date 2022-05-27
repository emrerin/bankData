package com.example.bankdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bankdata.adapter.RecyclerViewAdapter
import com.example.bankdata.databinding.ActivityMainBinding
import com.example.bankdata.factory.MyViewModelFactory
import com.example.bankdata.model.BDataModel
import com.example.bankdata.repository.MainRepository
import com.example.bankdata.service.RetrofitService
import com.example.bankdata.utils.Constants
import com.example.bankdata.view.BranchActivity
import com.example.bankdata.viewmodels.MainViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {

    private val TAG = "üMainActivity"
    lateinit var binding : ActivityMainBinding
    lateinit var viewModel: MainViewModel

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private val retrofitService = RetrofitService.getInstance()
    var adapter = RecyclerViewAdapter(this@MainActivity)

    companion object {
        const val BANK = "bank"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = Firebase.analytics

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        //set recyclerview adapter
        binding.recyclerView.adapter = adapter

        //get viewmodel instance using MyViewModelFactory
        viewModel =
            ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(
                MainViewModel::class.java
            )

        viewModel.bDataList.observe(this, Observer {
            Log.d(TAG, "bankInfoList: $it")
            adapter.setBankList(it)
        })

        viewModel.progressBar.observe(this, Observer {
            binding.progressBar.visibility=if(it){View.VISIBLE}else{View.GONE}
        })

        viewModel.errorMessage.observe(this, Observer {
            Log.d(TAG, "ğErrorMessage: $it")
        })

        if(Constants.isNetworkAvailable(context = this@MainActivity)) {
            viewModel.getAllBankData()
        } else {
            Constants.showAlert(context = this@MainActivity, "DİKKAT", "Lütfen internet bağlantınızı kontrol ediniz.")
        }

        // filter by city name
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                viewModel.filter(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //Toast.makeText(this@MainActivity, newText, Toast.LENGTH_SHORT).show()
                viewModel.filter(newText!!)
                return true
            }
        })
    }

    override fun onItemClick(bDataModel: BDataModel) {
        //Toast.makeText(this, "Clicked : ${bDataModel.dc_BANKA_SUBE}", Toast.LENGTH_LONG).show()

        val bundle = Bundle()
        bundle.apply {
            putInt(FirebaseAnalytics.Param.ITEM_NAME, bDataModel.ID)
            putString(FirebaseAnalytics.Param.ITEM_NAME, bDataModel.dc_SEHIR)
            putString(FirebaseAnalytics.Param.ITEM_NAME, bDataModel.dc_ILCE)
            putString(FirebaseAnalytics.Param.ITEM_NAME, bDataModel.dc_BANKA_SUBE)
            putString(FirebaseAnalytics.Param.ITEM_NAME, bDataModel.dc_BANKA_TIPI)
            putString(FirebaseAnalytics.Param.ITEM_NAME, bDataModel.dc_BANK_KODU)
            putString(FirebaseAnalytics.Param.ITEM_NAME, bDataModel.dc_ADRES_ADI)
            putString(FirebaseAnalytics.Param.ITEM_NAME, bDataModel.dc_ADRES)
            putString(FirebaseAnalytics.Param.ITEM_NAME, bDataModel.dc_POSTA_KODU)
            putString(FirebaseAnalytics.Param.ITEM_NAME, bDataModel.dc_ON_OFF_LINE)
            putString(FirebaseAnalytics.Param.ITEM_NAME, bDataModel.dc_ON_OFF_SITE)
            putString(FirebaseAnalytics.Param.ITEM_NAME, bDataModel.dc_BOLGE_KOORDINATORLUGU)
            putString(FirebaseAnalytics.Param.ITEM_NAME, bDataModel.dc_EN_YAKIM_ATM)
        }
        firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle)

        val intent = Intent(this, BranchActivity::class.java)
        intent.putExtra(BANK, bDataModel)
        startActivity(intent)
    }
}