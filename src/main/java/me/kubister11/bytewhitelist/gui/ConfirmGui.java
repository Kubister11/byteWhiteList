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
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class ConfirmGui implements InventoryProvider {

    private final Runnable runnable;

    public ConfirmGui(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        ItemStack filter = ItemUtils.createFilter(Material.GRAY_STAINED_GLASS_PANE, " ");
        contents.fill(ClickableItem.empty(filter, false));

        contents.set(1, 3, ClickableItem.of(ItemUtils.createIs(Material.LIME_DYE, Config.GUI_CONFIRM_ITEMS_CONFIRM, true), false, inventoryClickEvent -> {
            runnable.run();
        }));

        contents.set(1, 5, ClickableItem.of(ItemUtils.createIs(Material.RED_DYE, Config.GUI_CONFIRM_ITEMS_CANCEL, true), false, inventoryClickEvent -> {
            player.closeInventory();
        }));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    public static void open(Player p, Runnable runnable) {
        SmartInventory.builder()
                .provider(new ConfirmGui(runnable))
                .size(3, 9)
                .title(TextUtil.fix(Config.GUI_CONFIRM_TITLE))
                .build().open(p);
    }
}
