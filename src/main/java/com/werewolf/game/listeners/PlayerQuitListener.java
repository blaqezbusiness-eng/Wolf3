/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerQuitEvent
 */
package com.werewolf.game.listeners;

import com.werewolf.game.WerewolfPlugin;
import com.werewolf.game.arena.Arena;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener
implements Listener {
    private final WerewolfPlugin plugin;

    public PlayerQuitListener(WerewolfPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Arena arena = this.plugin.getArenaManager().getArenaByPlayer(event.getPlayer());
        if (arena != null) {
            arena.removePlayer(event.getPlayer());
        }
    }
}
