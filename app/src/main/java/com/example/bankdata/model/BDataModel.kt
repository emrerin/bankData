package com.example.bankdata.model

import android.os.Parcel
import android.os.Parcelable

/**
 * {
"ID": 1,
"dc_SEHIR": "ADANA",
"dc_ILCE": "CEYHAN",
"dc_BANKA_SUBE": "CEYHAN ŞUBESİ/ADANA",
"dc_BANKA_TIPI": "Para Çek Yatır",
"dc_BANK_KODU": "S1A01590",
"dc_ADRES_ADI": "CEYHAN ŞUBESİ",
"dc_ADRES": "Türlübaş Mah.Atatürk Cad.No:272 Ceyhan/ADANA 01960",
"dc_POSTA_KODU": "1960",
"dc_ON_OFF_LINE": "On Line",
"dc_ON_OFF_SITE": "On Site",
"dc_BOLGE_KOORDINATORLUGU": "ADANA BÖLGE",
"dc_EN_YAKIM_ATM": "ADANA / VİLAYET-ADANA-ATATÜRK BULVARI ŞUBESİ"
}
 *
 * */

data class BDataModel(
    val ID: Int,
    val dc_SEHIR: String?,
    val dc_ILCE: String?,
    val dc_BANKA_SUBE: String?,
    val dc_BANKA_TIPI: String?,
    val dc_BANK_KODU: String?,
    val dc_ADRES_ADI: String?,
    val dc_ADRES: String?,
    val dc_POSTA_KODU: String?,
    val dc_ON_OFF_LINE: String?,
    val dc_ON_OFF_SITE: String?,
    val dc_BOLGE_KOORDINATORLUGU: String?,
    val dc_EN_YAKIM_ATM: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ID)
        parcel.writeString(dc_SEHIR)
        parcel.writeString(dc_ILCE)
        parcel.writeString(dc_BANKA_SUBE)
        parcel.writeString(dc_BANKA_TIPI)
        parcel.writeString(dc_BANK_KODU)
        parcel.writeString(dc_ADRES_ADI)
        parcel.writeString(dc_ADRES)
        parcel.writeString(dc_POSTA_KODU)
        parcel.writeString(dc_ON_OFF_LINE)
        parcel.writeString(dc_ON_OFF_SITE)
        parcel.writeString(dc_BOLGE_KOORDINATORLUGU)
        parcel.writeString(dc_EN_YAKIM_ATM)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BDataModel> {
        override fun createFromParcel(parcel: Parcel): BDataModel {
            return BDataModel(parcel)
        }

        override fun newArray(size: Int): Array<BDataModel?> {
            return arrayOfNulls(size)
        }
    }
}