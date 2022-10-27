package de.fllip.home.spigot;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 27.10.22
 * Time: 21:41
 */
public class PlayerProfiles {

    public final static PlayerProfile RED_X_PROFILE = Bukkit.createProfile(UUID.randomUUID());

    private final static String RED_X_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmViNTg4YjIxYTZmOThhZDFmZjRlMDg1YzU1MmRjYjA1MGVmYzljYWI0MjdmNDYwNDhmMThmYzgwMzQ3NWY3In19fQ==";

    static {
        RED_X_PROFILE.setProperty(new ProfileProperty("textures", RED_X_TEXTURE));
    }

}
