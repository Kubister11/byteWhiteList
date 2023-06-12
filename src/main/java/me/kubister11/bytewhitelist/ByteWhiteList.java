package me.kubister11.bytewhitelist;

import fr.minuskube.inv.InventoryManager;
import lombok.Getter;
import me.kubister11.bytewhitelist.commands.MainCommand;
import me.kubister11.bytewhitelist.listeners.ChatListener;
import me.kubister11.bytewhitelist.listeners.LoginListener;
import me.kubister11.bytewhitelist.storage.files.Config;
import me.kubister11.bytewhitelist.storage.files.WhiteListData;
import me.kubister11.bytewhitelist.tasks.EditorsInfoTask;
import me.kubister11.bytewhitelist.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class ByteWhiteList extends JavaPlugin {

    @Getter private static Plugin plugin;
    @Getter private static InventoryManager invManager;

    @Override
    public void onEnable() {
        long startLoad = System.currentTimeMillis();
        plugin = this;

        invManager = new InventoryManager(this);
        invManager.init();

        new Config(this, Config.class, "config.yml");
        new WhiteListData(this, WhiteListData.class, "whitelist.yml");

        new LoginListener(this);
        new ChatListener(this);
        new MainCommand();

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new EditorsInfoTask(), 40L, 40L);

        TextUtil.sendToConsole("&ebyteWhiteList loaded! &7(took: " + (System.currentTimeMillis() - startLoad) + "ms)");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
