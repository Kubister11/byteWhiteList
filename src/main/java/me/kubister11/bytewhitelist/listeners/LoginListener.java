package me.kubister11.bytewhitelist.listeners;

import me.kubister11.bytewhitelist.managers.WhiteListManager;
import me.kubister11.bytewhitelist.storage.files.Config;
import me.kubister11.bytewhitelist.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;

public class LoginListener implements Listener {
    public LoginListener(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if (!Config.WHITELIST$STATUS) return;
        Player player = e.getPlayer();

        boolean contains = WhiteListManager.contains(player.getUniqueId(), player.getName());
        if (!contains) {
            if (player.hasPermission("bytewhitelist.bypass")) return;
            e.setResult(PlayerLoginEvent.Result.KICK_WHITELIST);
            e.setKickMessage(TextUtil.fix(Config.MESSAGES_WHITELIST$KICK));
        }
    }
}
