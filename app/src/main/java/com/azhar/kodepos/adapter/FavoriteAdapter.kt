package com.azhar.kodepos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.azhar.kodepos.R
import com.azhar.kodepos.model.ModelMain
import com.azhar.kodepos.realm.RealmHelper
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import kotlinx.android.synthetic.main.list_item_favorite.view.*

/**
 * Created by Azhar Rivaldi on 20-03-2021
 */

class FavoriteAdapter(private val context: Context, private val modelMain: List<ModelMain>) :
        RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    var realm: Realm? = null
    var helper: RealmHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_favorite, parent, false)
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
        holder.imageDelete.setOnClickListener { view ->
            helper!!.deleteKodePos(data.strKelurahan!!)
            modelMain.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(0, modelMain.size)
            notifyDataSetChanged()
            Snackbar.make(view, data.strKelurahan + " Removed from Favorite", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return modelMain.size
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageDelete: ImageView
        var tvProvinsi: TextView
        var tvKabupaten: TextView
        var tvKecamatan: TextView
        var tvKelurahan: TextView
        var tvKodePos: TextView

        init {
            imageDelete = itemView.imageDelete
            tvProvinsi = itemView.tvProvinsi
            tvKabupaten = itemView.tvKabupaten
            tvKecamatan = itemView.tvKecamatan
            tvKelurahan = itemView.tvKelurahan
            tvKodePos = itemView.tvKodePos
        }
    }

}