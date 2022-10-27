package de.fllip.home.common.config;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 27.10.22
 * Time: 14:22
 */
public record MessageConfig(
        String homeInventoryTitle,
        List<String> homeItemDescription,
        String deleteHomeItemName,
        String noPlayerWarningMessage,
        String noNameProvidedMessage,
        String deleteAllHomesItemName,
        String teleportFailedMessage,
        String homeNotFoundMessage,
        String createdHomeSuccessfullyMessage,
        String deletedHomeSuccessfullyMessage,
        String deletedAllHomesSuccessfullyMessage,
        String homeAlreadyExistsMessage
) {

    public MessageConfig() {
        this(
                "<green>Deine Homes</green>",
                Lists.newArrayList(
                        "<gray>Klicke, um dich zum Home zu teleportieren</gray>",
                        "",
                        "<gray>Server: <yellow><server></yellow>"
                ),
                "<red>Alle Homes löschen</red>",
                "<red>Du musst ein Spieler sein!</red>",
                "<red>Bitte gib einen Namen an!</red>",
                "<red>Alle Homes löschen</red>",
                "<red>Du konntest nicht teleportiert werden!</red>",
                "<red>Ein Home mit diesem Namen existiert nicht!</red>",
                "<green>Du hast erfolgreich ein neues Home erstellt</green>",
                "<green>Du hast dein Home erfolgreich gelöscht</green>",
                "<green>Du hast alle deine Homes erfolgreich gelöscht</green>",
                "<red>Du hast bereits ein Home mit diesen Namen!</red>"
        );
    }

}
