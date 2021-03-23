package com.azhar.kodepos.realm

import android.content.Context
import com.azhar.kodepos.model.ModelMain
import io.realm.Realm
import java.util.*

/**
 * Created by Azhar Rivaldi on 19-03-2021
 */

class RealmHelper(context: Context) {

    val realm: Realm

    init {
        Realm.init(context)
        realm = Realm.getDefaultInstance()
    }

    //menampilkan favorite
    fun showFavorite(): ArrayList<ModelMain> {
        val data = ArrayList<ModelMain>()
        val modelMainList = realm.where(ModelMain::class.java).findAll()
        if (modelMainList.size > 0) {
            for (i in modelMainList.indices) {
                val modelMain = ModelMain()
                modelMain.strProvinsi = modelMainList[i].strProvinsi
                modelMain.strKabupaten = modelMainList[i].strKabupaten
                modelMain.strKecamatan = modelMainList[i].strKecamatan
                modelMain.strKelurahan = modelMainList[i].strKelurahan
                modelMain.strKodePos = modelMainList[i].strKodePos
                data.add(modelMain)
            }
        }
        return data
    }

    //menambahkan favorite
    fun addFavorite(Provinsi: String, Kabupaten: String, Kecamatan: String, Kelurahan: String, KodePos: String) {
        val modelMain = ModelMain()
        modelMain.strProvinsi = Provinsi
        modelMain.strKabupaten = Kabupaten
        modelMain.strKecamatan = Kecamatan
        modelMain.strKelurahan = Kelurahan
        modelMain.strKodePos = KodePos
        realm.beginTransaction()
        realm.copyToRealm(modelMain)
        realm.commitTransaction()
    }

    //menghapus data favorite
    fun deleteKodePos(Kelurahan: String) {
        realm.beginTransaction()
        val modelMain = realm.where(ModelMain::class.java).equalTo("Kelurahan", Kelurahan).findAll()
        modelMain.deleteAllFromRealm()
        realm.commitTransaction()
    }

}