package me.kubister11.bytewhitelist.managers;

import me.kubister11.bytewhitelist.ByteWhiteList;
import me.kubister11.bytewhitelist.storage.files.Config;
import me.kubister11.bytewhitelist.storage.files.WhiteListData;
import me.kubister11.bytewhitelist.utils.TextUtil;
import me.kubister11.bytewhitelist.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WhiteListManager {

    public static boolean contains(UUID uuid, String nick) {
        if (WhiteListData.UUIDS.contains(uuid.toString())) return true;
        if (WhiteListData.NICKS.contains(nick)) {
            WhiteListData.NICKS.remove(nick);
            WhiteListData.UUIDS.add(uuid.toString());
            Utils.runAsync(ByteWhiteList.getPlugin(), () -> WhiteListData.getConfigFile().save());
            return true;
        }
        return false;
    }

    public static void remove(String nick, CommandSender sender) {
        if (WhiteListData.NICKS.contains(nick)) {
            WhiteListData.NICKS.remove(nick);
        } else {
            OfflinePlayer offlinePlayer = Bukkit.getServer().getPlayerExact(nick);
            if (offlinePlayer != null) {
                String uuid = offlinePlayer.getUniqueId().toString();
                if (WhiteListData.UUIDS.contains(uuid)) {
                    WhiteListData.UUIDS.remove(uuid);
                } else {
                    TextUtil.sendMessage(sender, Config.MESSAGES_PLAYER$NOT$FOUND.replace("[PLAYER]", nick));
                    return;
                }
            } else {
                TextUtil.sendMessage(sender, Config.MESSAGES_PLAYER$NOT$FOUND.replace("[PLAYER]", nick));
                return;
            }
        }

        Utils.runAsync(ByteWhiteList.getPlugin(), () -> WhiteListData.getConfigFile().save());
        TextUtil.sendMessage(sender, Config.MESSAGES_REMOVED$PLAYER.replace("[PLAYER]", nick));
    }

    public static void add(String nick, CommandSender sender) {
        OfflinePlayer offlinePlayer = Bukkit.getServer().getPlayerExact(nick);
        if (offlinePlayer == null) {
            if (WhiteListData.NICKS.contains(nick)) {
                TextUtil.sendMessage(sender, Config.MESSAGES_PLAYER$ALREADY$IN$WHITELIST.replace("[PLAYER]", nick));
                return;
            }
            WhiteListData.NICKS.add(nick);
        } else {
            if (WhiteListData.UUIDS.contains(offlinePlayer.getUniqueId().toString())) {
                TextUtil.sendMessage(sender, Config.MESSAGES_PLAYER$ALREADY$IN$WHITELIST.replace("[PLAYER]", nick));
                return;
            }
            WhiteListData.UUIDS.add(offlinePlayer.getUniqueId().toString());
        }
        Utils.runAsync(ByteWhiteList.getPlugin(), () -> WhiteListData.getConfigFile().save());
        TextUtil.sendMessage(sender, Config.MESSAGES_ADDED$PLAYER.replace("[PLAYER]", nick));
    }

    public static List<String> get() {
        ArrayList<String> strings = new ArrayList<>(WhiteListData.NICKS);
        WhiteListData.UUIDS.forEach(s -> strings.add(Bukkit.getOfflinePlayer(UUID.fromString(s)).getName()));
        return strings;
    }
}
