package dev.shop.gui;

import dev.shop.VolterShop;
import dev.shop.shop.ShopCategory;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class GUIManager {

    private final VolterShop plugin;

    private final Map<UUID, ShopCategory> categoryContext =
            new ConcurrentHashMap<>();

    public GUIManager(VolterShop plugin) {
        this.plugin = plugin;
    }

    public void openMain(Player player) {
        categoryContext.remove(player.getUniqueId());
        new MainMenu(plugin, this).open(player);
    }

    public void openCategory(Player player, ShopCategory category) {
        categoryContext.put(player.getUniqueId(), category);
        new CategoryMenu(plugin, this, category).open(player);
    }

    public void close(Player player) {
        categoryContext.remove(player.getUniqueId());
        player.closeInventory();
    }

    public void clear(Player player) {
        categoryContext.remove(player.getUniqueId());
    }
}
