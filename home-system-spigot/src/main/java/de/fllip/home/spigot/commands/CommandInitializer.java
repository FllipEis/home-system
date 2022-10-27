package de.fllip.home.spigot.commands;

import de.fllip.home.spigot.HomePlugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import java.util.Objects;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 25.10.22
 * Time: 18:42
 */
public class CommandInitializer {

    private final HomePlugin plugin;

    public CommandInitializer(HomePlugin plugin) {
        this.plugin = plugin;
    }

    public void initializeCommand(String name, CommandExecutor commandExecutor) {
        this.initializeCommand(name, commandExecutor, null);
    }

    public void initializeCommand(String name, CommandExecutor commandExecutor, TabCompleter tabCompleter) {
        PluginCommand command = Objects.requireNonNull(this.plugin.getCommand(name));
        command.setExecutor(commandExecutor);

        if (tabCompleter != null) {
            command.setTabCompleter(tabCompleter);
        }
    }

}
