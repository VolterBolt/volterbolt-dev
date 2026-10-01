package dev.shop.gui;

import dev.shop.VolterShop;
import dev.shop.shop.ShopCategory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public final class MainMenu {

    private final VolterShop plugin;
    private final GUIManager gui;

    public MainMenu(VolterShop plugin, GUIManager gui) {
        this.plugin = plugin;
        this.gui = gui;
    }

    public void open(Player player) {
        Holder holder = new Holder();

        Inventory inventory = Bukkit.createInventory(
                holder,
                54,
                plugin.color("&8✦ &6Volter Shop")
        );

        holder.inventory = inventory;

        fill(inventory);

        for (ShopCategory category : plugin.shopManager().categories()) {
            int slot = category.slot();

            if (slot < 0 || slot >= inventory.getSize()) continue;

            inventory.setItem(
                    slot,
                    button(
                            category.icon(),
                            category.displayName(),
                            List.of(
                                    plugin.color("&7Browse this category"),
                                    "",
                                    plugin.color("&e▸ Click to open")
                            )
                    )
            );
        }

        inventory.setItem(
                49,
                button(
                        Material.BARRIER,
                        "&c&lClose",
                        List.of("&7Close the shop.")
                )
        );

        player.openInventory(inventory);
    }

    private void fill(Inventory inventory) {
        ItemStack filler =
                button(Material.GRAY_STAINED_GLASS_PANE, " ", null);

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, filler);
        }
    }

    private ItemStack button(
            Material material,
            String name,
            List<String> lore
    ) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(plugin.color(name));

            if (lore != null) {
                meta.setLore(lore);
            }

            item.setItemMeta(meta);
        }

        return item;
    }

    private static final class Holder
            implements InventoryHolder {

        private Inventory inventory;

        @Override
        public Inventory getInventory() {
            return inventory;
        }
    }
}
