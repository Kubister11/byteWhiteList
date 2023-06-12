package me.kubister11.bytewhitelist.managers;

import lombok.Getter;
import me.kubister11.bytewhitelist.enums.ChatInputType;
import me.kubister11.bytewhitelist.gui.ListGui;
import me.kubister11.bytewhitelist.storage.files.Config;
import me.kubister11.bytewhitelist.utils.TextUtil;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatManager {
    @Getter private static final Map<UUID, ChatInputType> editors = new HashMap<>();

    public static boolean processInput(Player player, String input) {
        ChatInputType chatInputType = editors.get(player.getUniqueId());
        if (chatInputType == null) return false;

        switch (chatInputType) {
            case ADD_PLAYER -> WhiteListManager.add(input, player);
            case CHANGE_KICK_MESSAGE -> {
                Config.MESSAGES_WHITELIST$KICK = input;
                Config.getConfigFile().save();
                TextUtil.sendMessage(player, Config.MESSAGES_SAVED);
            }
        }

        ListGui.open(player, 0);
        editors.remove(player.getUniqueId());
        return true;
    }

}
