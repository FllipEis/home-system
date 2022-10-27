package de.fllip.home.spigot.commands;

import de.fllip.home.common.config.MessageConfig;
import de.fllip.home.spigot.inventory.HomeListInventory;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 25.10.22
 * Time: 18:40
 */
public class HomesCommand extends PlayerCommandExecutor {

    private final HomeListInventory homeListInventory;

    public HomesCommand(HomeListInventory homeListInventory, MessageConfig messageConfig) {
        super(messageConfig);
        this.homeListInventory = homeListInventory;
    }

    @Override
    boolean onCommand(@NotNull Player player, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.homeListInventory.openInventory(player);
        return true;
    }

}
