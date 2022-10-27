package de.fllip.home.spigot.commands;

import de.fllip.home.api.HomeAPI;
import de.fllip.home.common.config.MessageConfig;
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

    private final HomeAPI homeAPI;

    public HomesCommand(HomeAPI homeAPI, MessageConfig messageConfig) {
        super(messageConfig);
        this.homeAPI = homeAPI;
    }

    @Override
    boolean onCommand(@NotNull Player player, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return true;
    }

}
