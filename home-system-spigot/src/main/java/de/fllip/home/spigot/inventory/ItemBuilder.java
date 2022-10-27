package de.fllip.home.spigot.inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
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

    private final static NamespacedKey ITEM_IDENTIFIER_KEY = NamespacedKey.minecraft("item-identifier");

    private final ItemStack itemStack;

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(new ItemStack(material));
    }

    private ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder withIdentifier(String identifier) {
        return this.withIdentifier(ITEM_IDENTIFIER_KEY, identifier);
    }

    public ItemBuilder withIdentifier(NamespacedKey namespacedKey, String identifier) {
        this.itemStack.getItemMeta().getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, identifier);
        return this;
    }

    public ItemBuilder withDisplayName(String displayName) {
        return this.withDisplayName(MiniMessage.miniMessage().deserialize(displayName));
    }

    public ItemBuilder withDisplayName(Component displayName) {
        this.itemStack.getItemMeta().displayName(displayName);
        return this;
    }

    public ItemBuilder withMiniMessageLoreLines(List<String> loreLines) {
        return this.withLoreLines(
                loreLines.stream()
                        .map(lore -> MiniMessage.miniMessage().deserialize(lore))
                        .collect(Collectors.toList())
        );
    }

    public ItemBuilder withLoreLines(List<Component> loreLines) {
        this.itemStack.getItemMeta().lore(loreLines);
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }

}
