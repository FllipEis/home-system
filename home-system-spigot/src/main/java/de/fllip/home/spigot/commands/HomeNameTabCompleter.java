package de.fllip.home.spigot.commands;

import de.fllip.home.api.Home;
import de.fllip.home.api.HomeAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 27.10.22
 * Time: 22:40
 */
public class HomeNameTabCompleter implements TabCompleter {

    private final HomeAPI homeAPI;

    public HomeNameTabCompleter(HomeAPI homeAPI) {
        this.homeAPI = homeAPI;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return null;
        }

        if (args.length == 0) {
            return null;
        }

        String lastArg = args[args.length - 1].toLowerCase();

        try {
            return this.homeAPI.getHomeRepository().findAllHomesByOwnerId(player.getUniqueId()).get()
                    .stream()
                    .map(Home::name)
                    .filter(name -> name.toLowerCase().startsWith(lastArg))
                    .collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

}
