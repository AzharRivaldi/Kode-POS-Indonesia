package com.azhar.kodepos.model

import io.realm.RealmObject
import java.io.Serializable

/**
 * Created by Azhar Rivaldi on 19-03-2021
 */

class ModelMain() : RealmObject(), Serializable {

    var strProvinsi: String? = null
    var strKabupaten: String? = null
    var strKecamatan: String? = null
    var strKelurahan: String? = null
    var strKodePos: String? = null

}