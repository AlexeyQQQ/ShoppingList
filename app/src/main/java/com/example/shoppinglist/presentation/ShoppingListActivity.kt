package com.example.shoppinglist.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShoppingListActivity : AppCompatActivity() {

    private lateinit var viewModel: ShoppingListViewModel
    private lateinit var linearLayoutShopList: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)
        linearLayoutShopList = findViewById(R.id.linearLayoutShopList)

        viewModel = ViewModelProvider(this)[ShoppingListViewModel::class.java]
        viewModel.shopList.observe(this) {
            showList(it)
        }
    }

    private fun showList(list: List<ShopItem>) {
        linearLayoutShopList.removeAllViews()
        for (item in list) {
            val layoutId = if (item.enabled) {
                R.layout.item_shop_enabled
            } else {
                R.layout.item_shop_disabled
            }
            val view = LayoutInflater.from(this).inflate(layoutId, linearLayoutShopList, false)
            val textViewName = view.findViewById<TextView>(R.id.textViewName)
            val textViewCount = view.findViewById<TextView>(R.id.textViewCount)
            textViewName.text = item.name
            textViewCount.text = item.count.toString()
            view.setOnLongClickListener {
                viewModel.enabledShopItem(item)
                true
            }
            linearLayoutShopList.addView(view)
        }
    }
}