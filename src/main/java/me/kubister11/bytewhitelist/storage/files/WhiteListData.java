package me.kubister11.bytewhitelist.storage.files;

import lombok.Getter;
import me.kubister11.bytewhitelist.storage.api.ConfigFile;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class WhiteListData extends ConfigFile {
    @Getter private static ConfigFile configFile;
    public WhiteListData(Plugin plugin, Class configClass, String fileName) {
        super(plugin, configClass, fileName);
        configFile = this;
    }

    public static List<String> NICKS = new ArrayList<>();
    public static List<String> UUIDS = new ArrayList<>();
}
