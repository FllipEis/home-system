package de.fllip.home.spigot.commands;

import de.fllip.home.api.Home;
import de.fllip.home.api.HomeAPI;
import de.fllip.home.common.config.MessageConfig;
import de.fllip.home.spigot.MiniMessages;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 25.10.22
 * Time: 19:01
 */
public class SetHomeCommand extends PlayerCommandExecutor {

    private final HomeAPI homeAPI;

    private final MessageConfig messageConfig;

    public SetHomeCommand(HomeAPI homeAPI, MessageConfig messageConfig) {
        super(messageConfig);
        this.homeAPI = homeAPI;
        this.messageConfig = messageConfig;
    }

    @Override
    boolean onCommand(@NotNull Player player, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.getNameArgOrSendMessage(player, args).ifPresent(name -> {
            Location location = player.getLocation();
            Home home = new Home(
                    name,
                    player.getUniqueId(),
                    location.getWorld().getUID(),
                    location.getX(),
                    location.getY(),
                    location.getZ(),
                    location.getYaw(),
                    location.getPitch());

            this.homeAPI.getHomeRepository().saveHome(home)
                    .thenAccept(unused -> {
                        player.sendMessage(MiniMessages.of(this.messageConfig.createdHomeSuccessfullyMessage()));
                    })
                    .exceptionally(throwable -> {
                        player.sendMessage(MiniMessages.of(this.messageConfig.homeAlreadyExistsMessage()));
                        return null;
                    });
        });

        return true;
    }

}