package de.fllip.home.spigot.commands;

import de.fllip.home.api.HomeAPI;
import de.fllip.home.common.config.MessageConfig;
import de.fllip.home.spigot.MiniMessages;
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

    private final MessageConfig messageConfig;

    public DeleteHomeCommand(HomeAPI homeAPI, MessageConfig messageConfig) {
        super(messageConfig);
        this.homeAPI = homeAPI;
        this.messageConfig = messageConfig;
    }

    @Override
    public boolean onCommand(@NotNull Player player, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.getNameArgOrSendMessage(player, args).ifPresent(name -> {
            this.homeAPI.getHomeRepository().deleteHomeByNameAndOwnerId(name, player.getUniqueId())
                    .thenAccept(home -> {
                        player.sendMessage(MiniMessages.of(this.messageConfig.deletedHomeSuccessfullyMessage()));
                    })
                    .exceptionally(throwable -> {
                        player.sendMessage(MiniMessages.of(this.messageConfig.homeNotFoundMessage()));
                        return null;
                    });
        });

        return true;
    }

}