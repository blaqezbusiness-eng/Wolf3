package com.werewolf.game.gui;

import com.werewolf.game.WerewolfPlugin;
import com.werewolf.game.util.MessageUtil;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LanguageGUI {
    private static final Map<Player, Map<Integer, String>> slotActions = new HashMap<Player, Map<Integer, String>>();

    public static String getTitle(WerewolfPlugin plugin, Player player) {
        return plugin.getMessageUtil().get(player, "language.gui-title");
    }

    public static boolean isLanguageGUI(WerewolfPlugin plugin, Player player, String title) {
        if (title == null) {
            return false;
        }
        return ChatColor.stripColor(title).equalsIgnoreCase(ChatColor.stripColor(getTitle(plugin, player)));
    }

    public static void open(WerewolfPlugin plugin, Player player) {
        List<String> langs = plugin.getMessageUtil().getAvailableLanguages();
        int size = Math.min(54, Math.max(9, ((langs.size() + 8) / 9) * 9));
        Inventory inv = Bukkit.createInventory((InventoryHolder) player, size, getTitle(plugin, player));
        populate(plugin, player, inv);
        player.openInventory(inv);
    }

    private static void populate(WerewolfPlugin plugin, Player player, Inventory inv) {
        HashMap<Integer, String> actions = new HashMap<Integer, String>();
        inv.clear();
        List<String> langs = plugin.getMessageUtil().getAvailableLanguages();
        String current = plugin.getMessageUtil().getPlayerLanguage(player);
        int slot = 0;
        for (String lang : langs) {
            if (slot >= inv.getSize()) break;
            boolean selected = lang.equalsIgnoreCase(current);
            inv.setItem(slot, langItem(plugin, player, lang, selected));
            actions.put(slot, lang);
            ++slot;
        }
        slotActions.put(player, actions);
    }

    private static ItemStack langItem(WerewolfPlugin plugin, Player player, String lang, boolean selected) {
        ItemStack item = new ItemStack(selected ? Material.LIME_DYE : Material.GRAY_DYE);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            String display = (selected ? ChatColor.GREEN + "✔ " : ChatColor.YELLOW + "") + lang;
            meta.setDisplayName(display);
            String currentLine = plugin.getMessageUtil().get(player, "language.current", MessageUtil.ph("lang", currentSafe(plugin, player)));
            meta.setLore(Arrays.asList(
                ChatColor.GRAY + "Code: " + lang,
                selected ? ChatColor.GREEN + "Selected" : ChatColor.YELLOW + "Click to select",
                currentLine
            ));
            item.setItemMeta(meta);
        }
        return item;
    }

    private static String currentSafe(WerewolfPlugin plugin, Player player) {
        return plugin.getMessageUtil().getPlayerLanguage(player);
    }

    public static String getLangAtSlot(Player player, int slot) {
        Map<Integer, String> map = slotActions.get(player);
        return map == null ? null : map.get(slot);
    }

    public static void clearMapping(Player player) {
        slotActions.remove(player);
    }
}
