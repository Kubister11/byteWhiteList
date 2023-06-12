package me.kubister11.bytewhitelist.commands;

import me.kubister11.bytewhitelist.gui.ListGui;
import me.kubister11.bytewhitelist.managers.WhiteListManager;
import me.kubister11.bytewhitelist.storage.files.Config;
import me.kubister11.bytewhitelist.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {
    public MainCommand() {
        Bukkit.getPluginCommand("whitelist").setExecutor(this);
    }
    private static final String correctUse = "/whitelist [on, off, list, add, remove] <player>";
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("bytewhitelist.admin")) return true;

        if (args.length == 0) {
            if (!(sender instanceof Player p)) {
                TextUtil.sendMessage(sender, Config.MESSAGES_CORRECT$USE.replace("[COMMAND]", correctUse));
                return true;
            }
            ListGui.open(p, 0);
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("on")) {
                Config.WHITELIST$STATUS = true;
                TextUtil.sendMessage(sender, Config.MESSAGES_WHITELIST$ON);
                return true;
            } else if (args[0].equalsIgnoreCase("off")) {
                Config.WHITELIST$STATUS = false;
                TextUtil.sendMessage(sender, Config.MESSAGES_WHITELIST$OFF);
                return true;
            } else if (args[0].equalsIgnoreCase("list")) {
                String s = WhiteListManager.get().toString();
                TextUtil.sendMessage(sender, Config.MESSAGES_WHITELIST$LIST.replace("[LIST]", s.substring(1, s.length() - 1)));
                return true;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                WhiteListManager.add(args[1], sender);
                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                WhiteListManager.remove(args[1], sender);
                return true;
            }
        }
        TextUtil.sendMessage(sender, Config.MESSAGES_CORRECT$USE.replace("[COMMAND]", correctUse));
        return true;
    }


    private static final List<String> arguments = List.of("on", "off", "list", "add", "remove");
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> results = new ArrayList<>();
        if (args.length == 1) {
            for (String arg : arguments) {
                if (arg.startsWith(args[0].toLowerCase())) {
                    results.add(arg);
                }
            }
            return results;
        }
        return results;
    }
}
