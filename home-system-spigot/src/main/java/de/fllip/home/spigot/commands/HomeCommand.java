package de.fllip.home.spigot.commands;

import de.fllip.home.api.HomeAPI;
import de.fllip.home.common.config.MessageConfig;
import de.fllip.home.spigot.MiniMessages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 25.10.22
 * Time: 18:24
 */
public class HomeCommand extends PlayerCommandExecutor {

    private final HomeAPI homeAPI;

    private final MessageConfig messageConfig;

    public HomeCommand(HomeAPI homeAPI, MessageConfig messageConfig) {
        super(messageConfig);
        this.homeAPI = homeAPI;
        this.messageConfig = messageConfig;
    }

    @Override
    boolean onCommand(@NotNull Player player, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.getNameArgOrSendMessage(player, args).ifPresent(name -> {
            this.homeAPI.getHomeRepository().findHomeByHomeNameAndOwnerId(name, player.getUniqueId())
                    .thenAccept(home -> {
                        World world = Bukkit.getWorld(home.worldId());

                        if (world == null) {
                            player.sendMessage(MiniMessages.of(this.messageConfig.teleportFailedMessage()));
                            return;
                        }

                        Location location = new Location(world, home.x(), home.y(), home.z(), home.yaw(), home.pitch());
                        player.teleportAsync(location);
                    })
                    .exceptionally(throwable -> {
                        player.sendMessage(MiniMessages.of(this.messageConfig.homeNotFoundMessage()));
                        return null;
                    });
        });

        return true;
    }

}
