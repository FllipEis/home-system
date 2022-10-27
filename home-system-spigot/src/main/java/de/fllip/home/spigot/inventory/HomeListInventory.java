package de.fllip.home.spigot.inventory;

import de.fllip.home.api.Home;
import de.fllip.home.api.HomeAPI;
import de.fllip.home.common.config.MessageConfig;
import de.fllip.home.spigot.MiniMessages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 26.10.22
 * Time: 19:48
 */
public class HomeListInventory {

    private static final int INVENTORY_SIZE = 6 * 9;

    private static final ItemStack BACKGROUND_ITEM = ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).build();

    private final HomeAPI homeAPI;
    private final MessageConfig messageConfig;

    public HomeListInventory(HomeAPI homeAPI, MessageConfig messageConfig) {
        this.homeAPI = homeAPI;
        this.messageConfig = messageConfig;
    }

    public void openInventory(Player player) {
        Inventory inventory = this.createInventory(player);
        player.openInventory(inventory);
    }

    private Inventory createInventory(Player player) {
        Component title = MiniMessages.of(this.messageConfig.homeInventoryTitle());
        Inventory inventory = Bukkit.createInventory(null, INVENTORY_SIZE, title);
        this.fillInventory(player, inventory);
        return inventory;
    }

    private void fillInventory(Player player, Inventory inventory) {
        this.fillInventoryBackground(inventory);
        this.fillInventoryHomes(player, inventory);
        this.setDeleteAllHomesItem(inventory);
    }

    private void fillInventoryHomes(Player player, Inventory inventory) {
        this.homeAPI.getHomeRepository().findAllHomesByOwnerId(player.getUniqueId())
                .thenAccept(homes -> {
                    ItemStack[] items = homes.stream().map(this::createHomeItemStack).toArray(ItemStack[]::new);
                    inventory.addItem(items);
                });
    }

    private void setDeleteAllHomesItem(Inventory inventory) {
        ItemStack deleteItem = this.createDeleteItemStack();
        inventory.setItem(INVENTORY_SIZE - 5, deleteItem);
    }

    private void fillInventoryBackground(Inventory inventory) {
        for (int i = INVENTORY_SIZE - 9; i < INVENTORY_SIZE; i++) {
            inventory.setItem(i, BACKGROUND_ITEM);
        }
    }

    private ItemStack createHomeItemStack(Home home) {
        return ItemBuilder.of(Material.PAPER)
                .withIdentifier(InventoryIdentifiers.HOME_ITEM_IDENTIFIER)
                .withIdentifier(InventoryIdentifiers.HOME_IDENTIFIER_KEY, home.name())
                .withDisplayName(Component.text(home.name(), NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false))
                .withMiniMessageLoreLines(this.messageConfig.homeItemDescription())
                .build();
    }

    private ItemStack createDeleteItemStack() {
        return ItemBuilder.of(Material.PLAYER_HEAD)
                .withIdentifier(InventoryIdentifiers.DELETE_ITEM_IDENTIFIER)
                .withDisplayName(MiniMessages.of(this.messageConfig.deleteAllHomesItemName()))
                .build();
    }

}
