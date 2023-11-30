package com.example.shoppinglist.domain

class GetAllShopItemUseCase(private val shopItemRepository: ShopItemRepository) {

    fun getAllShopItem(): List<ShopItem> {
        return shopItemRepository.getAllShopItem()
    }
}