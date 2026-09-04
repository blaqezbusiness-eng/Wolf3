/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.plugin.Plugin
 */
package com.werewolf.game.listeners;

import com.werewolf.game.WerewolfPlugin;
import com.werewolf.game.arena.Arena;
import com.werewolf.game.game.Phase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class PlayerJoinListener
implements Listener {
    private final WerewolfPlugin plugin;

    public PlayerJoinListener(WerewolfPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Phase phase;
        Arena game;
        event.setJoinMessage(null);
        event.getPlayer().sendMessage(this.plugin.prefix() + this.plugin.getMessageUtil().get("game.welcome"));
        Location lobby = this.plugin.getArenaManager().getGlobalLobby();
        if (lobby != null) {
            Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, () -> event.getPlayer().teleport(lobby), 1L);
        }
        if ((game = this.plugin.getArenaManager().getGame()) != null && ((phase = game.getPhase()) == Phase.LOBBY || phase == Phase.DAY || phase == Phase.NIGHT || phase == Phase.SHERIFF_ELECTION)) {
            Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, () -> {
                if (!game.isPlayerInArena(event.getPlayer())) {
                    game.addPlayer(event.getPlayer());
                }
            }, 2L);
        }
    }
}
