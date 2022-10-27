package de.fllip.home.common.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.fllip.home.common.config.DatabaseConfig;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 26.10.22
 * Time: 14:14
 */
public class DataSourceFactory {

    public static HikariDataSource createDataSource(DatabaseConfig databaseConfig) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(databaseConfig.jdbcUrl());
        hikariConfig.setUsername(databaseConfig.username());
        hikariConfig.setPassword(databaseConfig.password());

        return new HikariDataSource(hikariConfig);
    }

}
