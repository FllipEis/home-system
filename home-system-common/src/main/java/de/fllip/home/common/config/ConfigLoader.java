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
            .create();

    public ConfigLoader() {
        throw new IllegalStateException("ConfigLoader cannot be initialized");
    }

    public static Config load(Path file) {
        if (!Files.exists(file)) {
            return saveNewFile(file);
        }

        return loadExistingConfig(file);
    }

    private static Config saveNewFile(Path file) {
        try {
            Files.createDirectories(file.getParent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Config config = new Config(
                new DatabaseConfig(),
                new MessageConfig()
        );

        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(
                    config,
                    writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return config;
    }

    private static Config loadExistingConfig(Path file) {
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            return GSON.fromJson(reader, Config.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
