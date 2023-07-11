package me.kubister11.bytewhitelist.gui;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.kubister11.bytewhitelist.ByteWhiteList;
import me.kubister11.bytewhitelist.managers.WhiteListManager;
import me.kubister11.bytewhitelist.storage.files.Config;
import me.kubister11.bytewhitelist.utils.ItemUtils;
import me.kubister11.bytewhitelist.utils.TextUtil;
import me.kubister11.bytewhitelist.utils.Utils;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListGui implements InventoryProvider {
    
    private final int page;

    public ListGui(int page) {
        this.page = page;
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        ItemStack filter1 = ItemUtils.createFilter(Material.BLACK_STAINED_GLASS_PANE, " ");
        for (int i = 0; i < 9; i++) {
            contents.set(4, i, ClickableItem.empty(filter1, false));
        }
        ItemStack filter2 = ItemUtils.createFilter(Material.GRAY_STAINED_GLASS_PANE, " ");
        for (int i = 0; i < 9; i++) {
            contents.set(5, i, ClickableItem.empty(filter2, false));
        }

        List<String> wl = WhiteListManager.get();

        int index = page * 36;
        for (int i = 0; i < 36; i++) {
            if (wl.size() <= index) continue;
            String nick = wl.get(index);

            ItemStack is = ItemUtils.createIs(Material.PLAYER_HEAD, Config.GUI_MAIN_ITEMS_HEAD_DISPLAY$NAME.replace("[PLAYER]", nick), Config.GUI_MAIN_ITEMS_HEAD_LORE, false);
            SkullMeta sm = (SkullMeta) is.getItemMeta();
            sm.setOwner(nick);
            is.setItemMeta(sm);

            contents.set(Utils.calcSpace(i), ClickableItem.of(is, false, inventoryClickEvent -> ConfirmGui.open(player, () -> {
                WhiteListManager.remove(nick, player);
                open(player, page);
            })));
            index++;
        }

        contents.set(5, 4, ClickableItem.of(
                ItemUtils.createIs(Config.WHITELIST$STATUS ? Material.LIME_DYE : Material.RED_DYE, Config.WHITELIST$STATUS ? Config.GUI_MAIN_ITEMS_STATUS$ON_DISPLAY$NAME : Config.GUI_MAIN_ITEMS_STATUS$OFF_DISPLAY$NAME, Config.WHITELIST$STATUS ? Config.GUI_MAIN_ITEMS_STATUS$ON_LORE : Config.GUI_MAIN_ITEMS_STATUS$OFF_LORE, Config.WHITELIST$STATUS), false,
                e -> {
                    Config.WHITELIST$STATUS = !Config.WHITELIST$STATUS;
                    open(player, page);
                    Utils.runAsync(ByteWhiteList.getPlugin(), () -> Config.getConfigFile().save());
        }));


        ArrayList<String> kickLore = new ArrayList<>(Config.GUI_MAIN_ITEMS_CHANGE$KICK$MESSAGE_LORE);
        kickLore.replaceAll(s -> s.replace("[MESSAGE]", Config.MESSAGES_WHITELIST$KICK));
        contents.set(5, 0, ClickableItem.of(
                ItemUtils.createIs(Material.PAPER, Config.GUI_MAIN_ITEMS_CHANGE$KICK$MESSAGE_DISPLAY$NAME, kickLore, false), false,
                e -> new AnvilGUI.Builder()
                        .title("Wpisz tekst")
                        .text(" ")
                        .plugin(ByteWhiteList.getPlugin())
                        .itemLeft(new ItemStack(Material.PAPER))
                        .onClick((slot, stateSnapshot) -> {
                            if (slot != AnvilGUI.Slot.OUTPUT) {
                                return Collections.emptyList();
                            }

                            Config.MESSAGES_WHITELIST$KICK = stateSnapshot.getText();
                            Config.getConfigFile().save();
                            return List.of(AnvilGUI.ResponseAction.run(() -> open(player, page)));
                        }).open(player)));
        contents.set(5, 1, ClickableItem.of(
                ItemUtils.createIs(Material.GREEN_DYE, Config.GUI_MAIN_ITEMS_ADD$PLAYER_DISPLAY$NAME, Config.GUI_MAIN_ITEMS_ADD$PLAYER_LORE, false), false,
                e -> new AnvilGUI.Builder()
                        .title("Wpisz nick gracza")
                        .text(" ")
                        .plugin(ByteWhiteList.getPlugin())
                        .itemLeft(new ItemStack(Material.PAPER))
                        .onClick((slot, stateSnapshot) -> {
                            if (slot != AnvilGUI.Slot.OUTPUT) {
                                return Collections.emptyList();
                            }

                            WhiteListManager.add(stateSnapshot.getText(), player);
                            return List.of(AnvilGUI.ResponseAction.run(() -> open(player, page)));
                        }).open(player)));

        contents.set(5, 7, ClickableItem.of(
                ItemUtils.createIs(Material.ARROW, "&f<--", false), false,
                e -> {
                    if ((page - 1) < 0) return;
                    open(player, page - 1);
                }));

        contents.set(5, 8, ClickableItem.of(
                ItemUtils.createIs(Material.ARROW, "&f-->", false), false,
                e -> open(player, page + 1)));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    public static void open(Player p, int page) {
        SmartInventory.builder()
                .provider(new ListGui(page))
                .size(6, 9)
                .title(TextUtil.fix(Config.GUI_MAIN_TITLE.replace("[PAGE]", String.valueOf(page))))
                .build().open(p);
    }
}
