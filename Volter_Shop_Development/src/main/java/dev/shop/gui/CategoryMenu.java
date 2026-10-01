package dev.shop.gui;

import dev.shop.VolterShop;
import dev.shop.shop.ShopCategory;
import dev.shop.shop.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public final class CategoryMenu {

    private static final int[] ITEM_SLOTS = {
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34,
            37, 38, 39, 40, 41, 42, 43
    };

    private final VolterShop plugin;
    private final GUIManager gui;
    private final ShopCategory category;

    public CategoryMenu(
            VolterShop plugin,
            GUIManager gui,
            ShopCategory category
    ) {
        this.plugin = plugin;
        this.gui = gui;
        this.category = category;
    }

    public void open(Player player) {
        Holder holder = new Holder();

        Inventory inventory = Bukkit.createInventory(
                holder,
                54,
                plugin.color(category.displayName())
        );

        holder.inventory = inventory;

        ItemStack filler =
                button(Material.GRAY_STAINED_GLASS_PANE, " ", null);

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, filler);
        }

        int shown = Math.min(
                category.items().size(),
                ITEM_SLOTS.length
        );

        for (int i = 0; i < shown; i++) {
            inventory.setItem(
                    ITEM_SLOTS[i],
                    itemButton(category.items().get(i))
            );
        }

        inventory.setItem(
                45,
                button(
                        Material.ARROW,
                        "&e&lBack",
                        List.of("&7Return to categories.")
                )
        );

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

    private ItemStack itemButton(ShopItem item) {
        ItemStack stack = new ItemStack(item.material());
        ItemMeta meta = stack.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(
                    plugin.color("&f" + pretty(item.material()))
            );

            meta.setLore(List.of(
                    plugin.color("&7Buy: &f" +
                            (item.canBuy()
                                    ? plugin.money(item.buyPrice())
                                    : "&cNot available")),

                    plugin.color("&7Sell: &f" +
                            (item.canSell()
                                    ? plugin.money(item.sellPrice())
                                    : "&cNot available")),

                    "",

                    plugin.color("&aLeft-click &7→ Buy"),
                    plugin.color("&cRight-click &7→ Sell")
            ));

            stack.setItemMeta(meta);
        }

        return stack;
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

    private static String pretty(Material material) {
        StringBuilder result = new StringBuilder();

        for (String word :
                material.name().toLowerCase().split("_")) {

            result.append(
                    Character.toUpperCase(word.charAt(0))
            );

            result.append(word.substring(1)).append(' ');
        }

        return result.toString().trim();
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
