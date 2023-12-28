package com.example.shoppinglist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class ShoppingListActivity : AppCompatActivity() {

    private lateinit var viewModel: ShoppingListViewModel
    private lateinit var recyclerViewShopList: RecyclerView
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)
        setupRecyclerView()

        viewModel = ViewModelProvider(this)[ShoppingListViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.shopList = it
        }
    }

    private fun setupRecyclerView() {
        recyclerViewShopList = findViewById(R.id.recyclerViewShopList)
        shopListAdapter = ShopListAdapter()

        with(recyclerViewShopList) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }

        setupAdapterClickListeners()
        setupItemTouchHelper()
    }

    private fun setupItemTouchHelper() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val shopItem = shopListAdapter.shopList[position]
                viewModel.deleteShopItem(shopItem)
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerViewShopList)
    }

    private fun setupAdapterClickListeners() {
        shopListAdapter.onShopItemClickListener = {
            println("Короткий клик по $it")
        }
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.enabledShopItem(it)
        }
    }

}