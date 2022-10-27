package de.fllip.home.spigot.commands;

import de.fllip.home.api.Home;
import de.fllip.home.api.HomeAPI;
import de.fllip.home.common.config.MessageConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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

    public SetHomeCommand(HomeAPI homeAPI, MessageConfig messageConfig) {
        super(messageConfig);
        this.homeAPI = homeAPI;
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
                        player.sendMessage(Component.text("Du hast erfolgreich ein neues Home erstellt!", NamedTextColor.GREEN));
                    })
                    .exceptionally(throwable -> {
                        player.sendMessage(Component.text("Ein Home mit diesem Namen existiert nicht!", NamedTextColor.RED));
                        return null;
                    });
        });

        return true;
    }

}