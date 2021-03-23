package com.azhar.kodepos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.azhar.kodepos.R
import com.azhar.kodepos.model.ModelMain
import com.azhar.kodepos.realm.RealmHelper
import com.github.ivbaranov.mfb.MaterialFavoriteButton
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import kotlinx.android.synthetic.main.list_item_main.view.*

/**
 * Created by Azhar Rivaldi on 19-03-2021
 */

class MainAdapter(private val context: Context, private val modelMain: MutableList<ModelMain>) :
        RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var realm: Realm? = null
    var helper: RealmHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_main, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = modelMain[position]

        helper = RealmHelper(context)
        realm = Realm.getDefaultInstance()

        holder.tvProvinsi.text = data.strProvinsi
        holder.tvKabupaten.text = data.strKabupaten
        holder.tvKecamatan.text = data.strKecamatan
        holder.tvKelurahan.text = data.strKelurahan
        holder.tvKodePos.text = data.strKodePos

        //add favorite
        holder.imageFavorite.setOnFavoriteChangeListener { buttonView, favorite ->
            if (favorite) {
                val Provinsi = data.strProvinsi
                val Kabupaten = data.strKabupaten
                val Kecamatan = data.strKecamatan
                val Kelurahan = data.strKelurahan
                val KodePos = data.strKodePos
                helper?.addFavorite(Provinsi, Kabupaten, Kecamatan, Kelurahan, KodePos)
                Snackbar.make(buttonView, data.strKelurahan + " Added to Favorite", Snackbar.LENGTH_SHORT).show()
            } else {
                helper?.deleteKodePos(data.strKelurahan)
                Snackbar.make(buttonView, data.strKelurahan + " Removed from Favorite", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return modelMain.size
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageFavorite: MaterialFavoriteButton
        var tvProvinsi: TextView
        var tvKabupaten: TextView
        var tvKecamatan: TextView
        var tvKelurahan: TextView
        var tvKodePos: TextView

        init {
            imageFavorite = itemView.imageFavorite
            tvProvinsi = itemView.tvProvinsi
            tvKabupaten = itemView.tvKabupaten
            tvKecamatan = itemView.tvKecamatan
            tvKelurahan = itemView.tvKelurahan
            tvKodePos = itemView.tvKodePos
        }
    }
}