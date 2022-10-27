package de.fllip.home.common.config;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 25.10.22
 * Time: 18:13
 */
public record Config(
        DatabaseConfig databaseConfig,
        MessageConfig messageConfig
) {
}
