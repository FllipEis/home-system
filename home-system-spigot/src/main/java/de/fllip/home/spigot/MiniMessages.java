package de.fllip.home.spigot;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 27.10.22
 * Time: 19:26
 */
public class MiniMessages {

    public static Component of(String message) {
        return MiniMessage.miniMessage()
                .deserialize(message)
                .decoration(TextDecoration.ITALIC, false);
    }

}
