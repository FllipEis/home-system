package de.fllip.home.spigot.inventory;

import de.fllip.home.spigot.MiniMessages;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 27.10.22
 * Time: 14:06
 */
public class ItemBuilder {

    private final ItemStack itemStack;

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(new ItemStack(material));
    }

    private ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder withIdentifier(String identifier) {
        return this.withIdentifier(InventoryIdentifiers.ITEM_IDENTIFIER_KEY, identifier);
    }

    public ItemBuilder withIdentifier(NamespacedKey namespacedKey, String identifier) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, identifier);
        this.itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder withDisplayName(String displayName) {
        return this.withDisplayName(MiniMessages.of(displayName));
    }

    public ItemBuilder withDisplayName(Component displayName) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.displayName(displayName);
        this.itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder withMiniMessageLoreLines(List<String> loreLines) {
        return this.withLoreLines(
                loreLines.stream()
                        .map(MiniMessages::of)
                        .collect(Collectors.toList())
        );
    }

    public ItemBuilder withLoreLines(List<Component> loreLines) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.lore(loreLines);
        this.itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }

}
