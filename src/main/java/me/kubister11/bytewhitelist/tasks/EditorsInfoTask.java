package me.kubister11.bytewhitelist.tasks;

import me.kubister11.bytewhitelist.managers.ChatManager;
import me.kubister11.bytewhitelist.storage.files.Config;
import me.kubister11.bytewhitelist.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class EditorsInfoTask implements Runnable {
    @Override
    public void run() {
        new HashMap<>(ChatManager.getEditors()).forEach((uuid, chatInputType) -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                player.sendTitle(TextUtil.fix(Config.MESSAGES_EDITOR_TITLE), TextUtil.fix(Config.MESSAGES_EDITOR_SUBTITLE), 20, 60, 20);
            } else {
                ChatManager.getEditors().remove(uuid);
            }
        });
    }
}
