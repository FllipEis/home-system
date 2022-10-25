package de.fllip.home.api;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 25.10.22
 * Time: 18:04
 */
public record Home(
        String name,
        UUID ownerId,
        UUID worldId,
        double x,
        double y,
        double z,
        float yaw,
        float pitch
) {
}
