package de.fllip.home.common.config;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 27.10.22
 * Time: 14:22
 */
public record MessageConfig(
        String homeInventoryTitle,
        String homeItemDescription,
        String deleteHomeItemName,
        String noPlayerWarningMessage,
        String noNameProvidedMessage
) {

    public MessageConfig() {
        this(
                "<green>Deine Homes</green>",
                "<gray>Klicke, um dich zum Home zu teleportieren</gray> <newline> <newline> <gray>Server: <yellow><server></yellow>",
                "<red>Alle Homes löschen</red>",
                "<red>Du musst ein Spieler sein!</red>",
                "<red>Bitte gib einen Namen an!</red>"
        );
    }

}
