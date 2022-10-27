package de.fllip.home.common.config;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 26.10.22
 * Time: 14:18
 */
public record DatabaseConfig(
        String jdbcUrl,
        String username,
        String password
) {

    public DatabaseConfig() {
        this(
                "jdbc:mysql://localhost:3306/",
                "root",
                "password"
        );
    }

}
