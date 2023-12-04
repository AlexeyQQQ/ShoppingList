package com.example.shoppinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.DeleteShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetAllShopItemUseCase
import com.example.shoppinglist.domain.ShopItem

class ShoppingListViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getAllShopItemUseCase = GetAllShopItemUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList: MutableLiveData<List<ShopItem>> = MutableLiveData<List<ShopItem>>()

    fun getAllShopItem() {
        shopList.value = getAllShopItemUseCase.getAllShopItem()
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
        getAllShopItem()
    }

    fun enabledShopItem(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(shopItem)
        getAllShopItem()
    }
}