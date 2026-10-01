package dev.shop.shop;

import org.bukkit.Material;

public record ShopItem(
        Material material,
        double buyPrice,
        double sellPrice
) {
    public boolean canBuy() {
        return buyPrice >= 0;
    }

    public boolean canSell() {
        return sellPrice >= 0;
    }
}
