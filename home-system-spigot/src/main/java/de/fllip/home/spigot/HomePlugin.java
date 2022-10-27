package de.fllip.home.spigot;

import de.fllip.home.api.HomeAPI;
import de.fllip.home.api.repository.HomeRepository;
import de.fllip.home.common.DefaultHomeAPI;
import de.fllip.home.common.config.Config;
import de.fllip.home.common.config.ConfigLoader;
import de.fllip.home.common.database.DataSourceFactory;
import de.fllip.home.common.repository.DefaultHomeRepository;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 25.10.22
 * Time: 18:24
 */
public class HomePlugin extends JavaPlugin {

    private final Config config = ConfigLoader.load(new File(this.getDataFolder(), "config.json"));

    private final HikariDataSource dataSource = DataSourceFactory.createDataSource(config.databaseConfig());

    private final HomeRepository homeRepository = new DefaultHomeRepository(this.dataSource);

    private final HomeAPI homeAPI = new DefaultHomeAPI(this.homeRepository);

    @Override
    public void onEnable() {
        Bukkit.getServicesManager().register(HomeAPI.class, this.homeAPI, this, ServicePriority.Normal);
    }

}
