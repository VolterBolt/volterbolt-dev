package dev.shop.shop;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ShopManager {

    private final Map<String, ShopCategory> categories =
            new LinkedHashMap<>();

    public void load(FileConfiguration config) {
        categories.clear();

        ConfigurationSection root =
                config.getConfigurationSection("categories");

        if (root == null) return;

        int autoSlot = 0;

        for (String id : root.getKeys(false)) {
            ConfigurationSection section =
                    root.getConfigurationSection(id);

            if (section == null) continue;

            ArrayList<ShopItem> items = new ArrayList<>();

            for (Map<?, ?> raw : section.getMapList("items")) {
                Material material =
                        Material.matchMaterial(
                                String.valueOf(raw.get("material"))
                        );

                if (material == null || !material.isItem()) continue;

                items.add(new ShopItem(
                        material,
                        number(raw.get("buy")),
                        number(raw.get("sell"))
                ));
            }

            int slot = section.getInt("slot", -1);

            if (slot < 0) {
                while (containsSlot(autoSlot)) autoSlot++;
                slot = autoSlot++;
            }

            Material icon = Material.matchMaterial(
                    section.getString("icon", "CHEST")
            );

            categories.put(
                    id,
                    new ShopCategory(
                            id,
                            section.getString("name", id),
                            icon == null ? Material.CHEST : icon,
                            slot,
                            items
                    )
            );
        }
    }

    public Collection<ShopCategory> categories() {
        return categories.values();
    }

    public ShopCategory category(String id) {
        return categories.get(id);
    }

    private boolean containsSlot(int slot) {
        return categories.values().stream()
                .anyMatch(category -> category.slot() == slot);
    }

    private static double number(Object value) {
        return value instanceof Number number
                ? number.doubleValue()
                : -1;
    }
}
