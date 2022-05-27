package com.example.bankdata.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bankdata.MainActivity
import com.example.bankdata.databinding.ActivityBranchBinding
import com.example.bankdata.model.BDataModel
import com.example.bankdata.utils.Constants


private val TAG = "üBranchActivity"
lateinit var binding : ActivityBranchBinding

private var bankModel: BDataModel? = null

class BranchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBranchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Branch Details"
        // set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        intent?.let {
            bankModel = intent.extras?.getParcelable(MainActivity.BANK)
            binding.cityName.text = binding.cityName.text.toString() + bankModel?.dc_SEHIR
            binding.districtName.text = binding.districtName.text.toString() + bankModel?.dc_ILCE
            binding.branchName.text = binding.branchName.text.toString() + bankModel?.dc_BANKA_SUBE
            binding.bankTypeName.text = binding.bankTypeName.text.toString() + bankModel?.dc_BANKA_TIPI
            binding.bankCodeName.text = binding.bankCodeName.text.toString() + bankModel?.dc_BANK_KODU
            binding.bankAdressName.text = binding.bankAdressName.text.toString() + bankModel?.dc_ADRES_ADI
            binding.bankAdress.text = binding.bankAdress.text.toString() + bankModel?.dc_ADRES
            binding.bankPostCode.text = binding.bankPostCode.text.toString() + bankModel?.dc_POSTA_KODU
            binding.onOffLine.text = binding.onOffLine.text.toString() + bankModel?.dc_ON_OFF_LINE
            binding.onOffSite.text = binding.onOffSite.text.toString() + bankModel?.dc_ON_OFF_SITE
            binding.regionCoordinatory.text = binding.regionCoordinatory.text.toString() + bankModel?.dc_BOLGE_KOORDINATORLUGU
            binding.nearestAtm.text = binding.nearestAtm.text.toString() + bankModel?.dc_EN_YAKIM_ATM
        }

        // assume map app installed on device {com.google.android.apps.maps}
        // example -> : Türlübaş Mah.Atatürk Cad.No:272 Ceyhan/ADANA 01960
        binding.goBranchNav.setOnClickListener {
            val adress = bankModel?.dc_ADRES
            if(adress.isNullOrEmpty()) {
                Constants.showAlert(this, "DİKKAT", "Şu anda \'Şube Adres\' bilgisine ulaşamıyoruz.")
            } else {
                val url = "http://maps.google.co.in/maps?q=${adress}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
                // finish() -> optional*/
            }
        }
    }

    // navigate previous screen
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}