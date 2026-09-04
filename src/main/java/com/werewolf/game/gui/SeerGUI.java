/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.SkullMeta
 */
package com.werewolf.game.gui;

import com.werewolf.game.WerewolfPlugin;
import com.werewolf.game.arena.Arena;
import com.werewolf.game.game.GamePlayer;
import com.werewolf.game.util.MessageUtil;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class SeerGUI {
    private static final Map<Player, Map<Integer, Player>> slotActions = new HashMap<Player, Map<Integer, Player>>();

    public static String getTitle(WerewolfPlugin plugin) {
        return plugin.getMessageUtil().get("gui.seer-title");
    }

    public static boolean isSeerGUI(WerewolfPlugin plugin, String title) {
        if (title == null) {
            return false;
        }
        return ChatColor.stripColor((String)title).equalsIgnoreCase(ChatColor.stripColor((String)SeerGUI.getTitle(plugin)));
    }

    public static void open(WerewolfPlugin plugin, Arena arena, Player seer) {
        Inventory inv = Bukkit.createInventory((InventoryHolder)seer, inventorySizeForPlayers(Math.max(1, arena.getAlivePlayers().size())), (String)SeerGUI.getTitle(plugin));
        SeerGUI.populate(plugin, arena, inv, seer);
        seer.openInventory(inv);
    }

    public static void update(WerewolfPlugin plugin, Arena arena, Player seer) {
        Inventory inv = seer.getOpenInventory().getTopInventory();
        if (inv == null || !SeerGUI.isSeerGUI(plugin, seer.getOpenInventory().getTitle())) {
            return;
        }
        SeerGUI.populate(plugin, arena, inv, seer);
        seer.updateInventory();
    }

    private static void populate(WerewolfPlugin plugin, Arena arena, Inventory inv, Player seer) {
        HashMap<Integer, Player> actions = new HashMap<Integer, Player>();
        inv.clear();
        int slot = 0;
        for (GamePlayer gp : arena.getAlivePlayers()) {
            if (gp.getPlayer().getUniqueId().equals(seer.getUniqueId())) continue;
            if (slot >= inv.getSize()) break;
            inv.setItem(slot, SeerGUI.skull(plugin, gp.getPlayer(), "gui-items.seer-player"));
            actions.put(slot, gp.getPlayer());
            ++slot;
        }
        slotActions.put(seer, actions);
    }

    public static Player getPlayerAtSlot(Player seer, int slot) {
        Map<Integer, Player> map = slotActions.get(seer);
        return map == null ? null : map.get(slot);
    }

    public static void clearMapping(Player seer) {
        slotActions.remove(seer);
    }

    private static ItemStack skull(WerewolfPlugin plugin, Player target, String nameKey) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta)item.getItemMeta();
        if (meta != null) {
            meta.setOwningPlayer((OfflinePlayer)target);
            Object name = plugin.getMessageUtil().get(nameKey, MessageUtil.ph("player", target.getName()));
            if (name == null || ((String)name).isEmpty()) {
                name = String.valueOf(ChatColor.YELLOW) + target.getName();
            }
            meta.setDisplayName((String)name);
            meta.setLore(Arrays.asList(String.valueOf(ChatColor.GRAY) + "Click to select"));
            item.setItemMeta((ItemMeta)meta);
        }
        return item;
    }

    /** Inventory size: 9 slots per row, sized to fit player count (max 54). */
    public static int inventorySizeForPlayers(int playerCount) {
        int count = Math.max(1, playerCount);
        int rows = (count + 8) / 9;
        if (rows < 1) rows = 1;
        if (rows > 6) rows = 6;
        return rows * 9;
    }
}
