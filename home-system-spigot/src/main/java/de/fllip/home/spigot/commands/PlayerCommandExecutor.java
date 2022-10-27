package de.fllip.home.spigot.commands;

import de.fllip.home.common.config.MessageConfig;
import de.fllip.home.spigot.MiniMessages;
import org.apache.logging.log4j.util.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 27.10.22
 * Time: 19:03
 */
public abstract class PlayerCommandExecutor implements CommandExecutor {

    private final MessageConfig messageConfig;

    protected PlayerCommandExecutor(MessageConfig messageConfig) {
        this.messageConfig = messageConfig;
    }

    abstract boolean onCommand(@NotNull Player player, @NotNull Command command, @NotNull String label, @NotNull String[] args);

    @Override
    public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(MiniMessages.of(this.messageConfig.noPlayerWarningMessage()));
            return false;
        }

        return this.onCommand(player, command, label, args);
    }

    protected Optional<String> getNameArgOrSendMessage(Player player, String[] args) {
        Optional<String> nameArg = this.getNameArg(args);

        if (nameArg.isEmpty()) {
            player.sendMessage(MiniMessages.of(this.messageConfig.noNameProvidedMessage()));
        }

        return nameArg;
    }

    protected Optional<String> getNameArg(String[] args) {
        if (args.length < 1) {
            return Optional.empty();
        }

        return Optional.of(Strings.join(Arrays.asList(args), '-'));
    }

}
