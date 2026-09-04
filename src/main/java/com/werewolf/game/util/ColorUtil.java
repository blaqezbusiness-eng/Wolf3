/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package com.werewolf.game.util;

import org.bukkit.ChatColor;

public class ColorUtil {
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)message);
    }
}
