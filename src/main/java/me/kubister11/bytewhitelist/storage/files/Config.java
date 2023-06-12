package me.kubister11.bytewhitelist.storage.files;

import lombok.Getter;
import me.kubister11.bytewhitelist.storage.api.ConfigFile;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Config extends ConfigFile {
    @Getter private static ConfigFile configFile;
    public Config(Plugin plugin, Class configClass, String fileName) {
        super(plugin, configClass, fileName);
        configFile = this;
    }

    public static String MESSAGES_CORRECT$USE = "&7Poprawne użycie: &f[COMMAND]";
    public static String MESSAGES_WHITELIST$KICK = "&cNa serwerze jest włączona whitelista!";
    public static String MESSAGES_SAVED = "&aZapisano zmiany!";
    public static String MESSAGES_PLAYER$NOT$FOUND = "&cNie znaleziono gracza [PLAYER]!";
    public static String MESSAGES_PLAYER$ALREADY$IN$WHITELIST = "&cGracz [PLAYER] jest już na whiteliście!";
    public static String MESSAGES_WHITELIST$ON = "&aWłączono whitelistę!";
    public static String MESSAGES_WHITELIST$OFF = "&cWyłączono whitelistę!";
    public static String MESSAGES_WHITELIST$LIST = "&cLista graczy na whiteliście: [LIST]";
    public static String MESSAGES_ADDED$PLAYER = "&aDodano gracza [PLAYER] do whitelisty!";
    public static String MESSAGES_REMOVED$PLAYER = "&cUsunięto gracza [PLAYER] z whitelisty!";
    public static String MESSAGES_EDITOR_TITLE = "&2EDYTOR";
    public static String MESSAGES_EDITOR_SUBTITLE = "&aWpisz wartość na chacie.";


    public static String GUI_MAIN_TITLE = "&0Whitelista: &f[PAGE]";

    public static String GUI_MAIN_ITEMS_STATUS$ON_DISPLAY$NAME = "&aWłączona";
    public static List<String> GUI_MAIN_ITEMS_STATUS$ON_LORE = new ArrayList<>();
    public static String GUI_MAIN_ITEMS_STATUS$OFF_DISPLAY$NAME = "&cWyłączona";
    public static List<String> GUI_MAIN_ITEMS_STATUS$OFF_LORE = new ArrayList<>();
    public static String GUI_MAIN_ITEMS_CHANGE$KICK$MESSAGE_DISPLAY$NAME = "&9Kliknij, aby zmienić wiadomość kicka.";
    public static List<String> GUI_MAIN_ITEMS_CHANGE$KICK$MESSAGE_LORE = List.of(" ", "&7Aktualna wiadomość:", "[MESSAGE]", " ");
    public static String GUI_MAIN_ITEMS_ADD$PLAYER_DISPLAY$NAME = "&aDodaj Gracza";
    public static List<String> GUI_MAIN_ITEMS_ADD$PLAYER_LORE = new ArrayList<>();
    public static String GUI_MAIN_ITEMS_HEAD_DISPLAY$NAME = "&f[PLAYER]";
    public static List<String> GUI_MAIN_ITEMS_HEAD_LORE = List.of(" ", "&8>> &7Kliknij aby usunąć z whitelisty!");


    public static String GUI_CONFIRM_TITLE = "&7Napewno?";

    public static String GUI_CONFIRM_ITEMS_CONFIRM = "&aPotwierdz!";
    public static String GUI_CONFIRM_ITEMS_CANCEL = "&cAnuluj!";

    public static boolean WHITELIST$STATUS = false;
}
