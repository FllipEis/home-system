package de.fllip.home.spigot.commands;

import de.fllip.home.api.HomeAPI;
import de.fllip.home.common.config.MessageConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 25.10.22
 * Time: 19:01
 */
public class DeleteHomeCommand extends PlayerCommandExecutor {

    private final HomeAPI homeAPI;
    public DeleteHomeCommand(HomeAPI homeAPI, MessageConfig messageConfig) {
        super(messageConfig);
        this.homeAPI = homeAPI;
    }

    @Override
    public boolean onCommand(@NotNull Player player, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.getNameArgOrSendMessage(player, args).ifPresent(name -> {
            this.homeAPI.getHomeRepository().deleteHomeByNameAndOwnerId(name, player.getUniqueId())
                    .thenAccept(home -> {
                        player.sendMessage(Component.text("Du hast dein Home erfolgreich gelöscht!", NamedTextColor.GREEN));
                    })
                    .exceptionally(throwable -> {
                        player.sendMessage(Component.text("Ein Home mit diesem Namen existiert nicht!", NamedTextColor.RED));
                        return null;
                    });
        });

        return true;
    }

}