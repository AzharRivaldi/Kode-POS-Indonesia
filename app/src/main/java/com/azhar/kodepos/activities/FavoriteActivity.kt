package com.azhar.kodepos.activities

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.azhar.kodepos.R
import com.azhar.kodepos.adapter.FavoriteAdapter
import com.azhar.kodepos.model.ModelMain
import com.azhar.kodepos.realm.RealmHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import java.util.*

class FavoriteActivity : AppCompatActivity() {

    var modelMainList: List<ModelMain> = ArrayList()
    var helper: RealmHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }

        helper = RealmHelper(this)

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        rvListKodePos.setLayoutManager(LinearLayoutManager(this))
        rvListKodePos.setHasFixedSize(true)

        getFavorite()
    }

    private fun getFavorite() {
        modelMainList = helper!!.showFavorite()
        if (modelMainList.size == 0) {
            tvNoData.visibility = View.VISIBLE
            rvListKodePos.visibility = View.GONE
        } else {
            tvNoData.visibility = View.GONE
            rvListKodePos.visibility = View.VISIBLE
            rvListKodePos.adapter = FavoriteAdapter(this, modelMainList as ArrayList<ModelMain>)
        }
    }

    override fun onResume() {
        super.onResume()
        getFavorite()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val window = activity.window
            val layoutParams = window.attributes
            if (on) {
                layoutParams.flags = layoutParams.flags or bits
            } else {
                layoutParams.flags = layoutParams.flags and bits.inv()
            }
            window.attributes = layoutParams
        }
    }

}