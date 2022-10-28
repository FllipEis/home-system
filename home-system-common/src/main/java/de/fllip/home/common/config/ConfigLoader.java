package de.fllip.home.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 26.10.22
 * Time: 16:06
 */
public class ConfigLoader {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    private ConfigLoader() {
        throw new IllegalStateException("ConfigLoader cannot be initialized");
    }

    public static Config load(Path path) {
        if (Files.notExists(path)) {
            return saveNewFile(path);
        }

        return loadExistingConfig(path);
    }

    private static Config saveNewFile(Path path) {
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Config config = new Config(
                new DatabaseConfig(),
                new MessageConfig()
        );

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return config;
    }

    private static Config loadExistingConfig(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return GSON.fromJson(reader, Config.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
