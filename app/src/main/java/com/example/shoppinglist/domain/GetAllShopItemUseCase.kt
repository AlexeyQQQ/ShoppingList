package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

class GetAllShopItemUseCase(private val shopItemRepository: ShopItemRepository) {

    fun getAllShopItem(): LiveData<List<ShopItem>> {
        return shopItemRepository.getAllShopItem()
    }
}