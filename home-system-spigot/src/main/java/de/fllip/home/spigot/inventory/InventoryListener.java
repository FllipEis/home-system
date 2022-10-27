package de.fllip.home.spigot.inventory;

import de.fllip.home.api.HomeAPI;
import de.fllip.home.common.config.MessageConfig;
import de.fllip.home.spigot.MiniMessages;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 27.10.22
 * Time: 20:33
 */
public class InventoryListener implements Listener {

    private final HomeAPI homeAPI;

    private final MessageConfig messageConfig;

    public InventoryListener(HomeAPI homeAPI, MessageConfig messageConfig) {
        this.homeAPI = homeAPI;
        this.messageConfig = messageConfig;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        InventoryView inventoryView = player.getOpenInventory();
        if (this.checkInventoryName(inventoryView)) {
            return;
        }

        event.setResult(Event.Result.DENY);

        ItemStack currentItem = event.getCurrentItem();
        if (currentItem == null) {
            return;
        }

        PersistentDataContainer dataContainer = currentItem.getItemMeta().getPersistentDataContainer();
        if (!dataContainer.has(InventoryIdentifiers.ITEM_IDENTIFIER_KEY)) {
            return;
        }

        switch (Objects.requireNonNull(dataContainer.get(InventoryIdentifiers.ITEM_IDENTIFIER_KEY, PersistentDataType.STRING))) {
            case InventoryIdentifiers.HOME_ITEM_IDENTIFIER -> this.handleHomeItemClick(player, dataContainer);
            case InventoryIdentifiers.DELETE_ITEM_IDENTIFIER -> this.handleDeleteHomesItemClick(player);
        }
    }

    private void handleHomeItemClick(Player player, PersistentDataContainer dataContainer) {
        player.closeInventory();

        String homeName = dataContainer.get(InventoryIdentifiers.HOME_IDENTIFIER_KEY, PersistentDataType.STRING);
        player.performCommand("home " + homeName);
    }

    private void handleDeleteHomesItemClick(Player player) {
        player.closeInventory();
        this.homeAPI.getHomeRepository().deleteAllHomeByOwnerId(player.getUniqueId());
        player.sendMessage(MiniMessages.of(this.messageConfig.deletedAllHomesSuccessfullyMessage()));
    }

    private boolean checkInventoryName(InventoryView inventoryView) {
        if (!((inventoryView.title()) instanceof TextComponent component)) {
            return false;
        }

        if (!component.content().equals(this.messageConfig.homeInventoryTitle())) {
            return false;
        }

        return true;
    }

}
