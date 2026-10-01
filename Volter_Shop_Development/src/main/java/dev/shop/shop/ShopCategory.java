package dev.shop.shop;

import org.bukkit.Material;

import java.util.List;

public record ShopCategory(
        String id,
        String displayName,
        Material icon,
        int slot,
        List<ShopItem> items
) {
    public ShopCategory {
        items = List.copyOf(items);
    }
}
