package com.azhar.kodepos.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.azhar.kodepos.R
import com.azhar.kodepos.adapter.MainAdapter
import com.azhar.kodepos.model.ModelMain
import com.azhar.kodepos.networking.ApiEndpoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    var mainAdapter: MainAdapter? = null
    var modelMain: MutableList<ModelMain> = ArrayList()
    var strInputTeks: String = ""
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = ProgressDialog(this)
        progressDialog?.setTitle("Mohon Tunggu...")
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Sedang menampilkan data")

        imageClear.setVisibility(View.GONE)
        linearHasil.setVisibility(View.GONE)

        rvListKodePos.setLayoutManager(LinearLayoutManager(this))
        rvListKodePos.setHasFixedSize(true)

        //membuka menu favorite
        imageFavorite.setOnClickListener {
            val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
            startActivity(intent)
        }

        //menghapus list hasil
        imageClear.setOnClickListener {
            teksInput.getText().clear()
            modelMain.clear()
            linearHasil.setVisibility(View.GONE)
            imageClear.setVisibility(View.GONE)
        }

        //action menampilkan data
        btnPencarian.setOnClickListener {
            strInputTeks = teksInput.getText().toString()
            if (strInputTeks.isEmpty()) {
                Toast.makeText(this@MainActivity, "Form tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            } else {
                //menampilkan data
                getKodePos(strInputTeks)
            }
        }
    }

    private fun getKodePos(strInputTeks: String) {
        progressDialog?.show()
        modelMain.clear()
        AndroidNetworking.get(ApiEndpoint.BASEURL)
                .addPathParameter("query", strInputTeks)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        progressDialog?.dismiss()
                        try {
                            val jsonArray = response.getJSONArray("data")
                            for (i in 0 until jsonArray.length()) {
                                val jsonObjectKata = jsonArray.getJSONObject(i)
                                val dataModel = ModelMain()
                                dataModel.strProvinsi = jsonObjectKata.getString("province")
                                dataModel.strKabupaten = jsonObjectKata.getString("city")
                                dataModel.strKecamatan = jsonObjectKata.getString("subdistrict")
                                dataModel.strKelurahan = jsonObjectKata.getString("urban")
                                dataModel.strKodePos = jsonObjectKata.getString("postalcode")
                                modelMain.add(dataModel)
                            }
                            mainAdapter = MainAdapter(this@MainActivity, modelMain)
                            rvListKodePos.adapter = mainAdapter
                            mainAdapter?.notifyDataSetChanged()
                            imageClear.visibility = View.VISIBLE
                            linearHasil.visibility = View.VISIBLE
                        } catch (e: JSONException) {
                            Toast.makeText(this@MainActivity, "Oops, gagal menampilkan jenis dokumen.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(anError: ANError) {
                        progressDialog?.dismiss()
                        Toast.makeText(this@MainActivity, "Oops! Sepertinya ada masalah dengan koneksi internet kamu.",
                                Toast.LENGTH_SHORT).show()
                    }
                })
    }
}