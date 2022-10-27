package de.fllip.home.spigot;

import de.fllip.home.api.HomeAPI;
import de.fllip.home.api.repository.HomeRepository;
import de.fllip.home.common.DefaultHomeAPI;
import de.fllip.home.common.config.Config;
import de.fllip.home.common.config.ConfigLoader;
import de.fllip.home.common.config.MessageConfig;
import de.fllip.home.common.database.DataSourceFactory;
import de.fllip.home.common.repository.DefaultHomeRepository;
import de.fllip.home.spigot.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import com.zaxxer.hikari.HikariDataSource;

import java.nio.file.Path;
import java.util.logging.Level;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 25.10.22
 * Time: 18:24
 */
public class HomePlugin extends JavaPlugin {

    private final Config config = ConfigLoader.load(Path.of(this.getDataFolder().toString(), "config.json"));

    private final HikariDataSource dataSource = DataSourceFactory.createDataSource(config.databaseConfig());

    private final HomeRepository homeRepository = new DefaultHomeRepository(this.dataSource);

    private final HomeAPI homeAPI = new DefaultHomeAPI(this.homeRepository);

    @Override
    public void onEnable() {
        Bukkit.getServicesManager().register(HomeAPI.class, this.homeAPI, this, ServicePriority.Normal);
        this.homeRepository.createTable()
                .exceptionally(throwable -> {
                    this.getLogger().log(Level.WARNING, "Error while creating 'homes' table");
                    return null;
                });

        this.initializeCommands();
    }

    private void initializeCommands() {
        CommandInitializer commandInitializer = new CommandInitializer(this);
        MessageConfig messageConfig = this.config.messageConfig();

        commandInitializer.initializeCommand("homes", new HomesCommand(this.homeAPI, messageConfig));
        commandInitializer.initializeCommand("home", new HomeCommand(this.homeAPI, messageConfig));
        commandInitializer.initializeCommand("sethome", new SetHomeCommand(this.homeAPI, messageConfig));
        commandInitializer.initializeCommand("delhome", new DeleteHomeCommand(this.homeAPI, messageConfig));
    }

}
