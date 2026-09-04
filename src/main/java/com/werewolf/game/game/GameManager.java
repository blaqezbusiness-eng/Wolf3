/*
 * Decompiled with CFR 0.152.
 */
package com.werewolf.game.game;

import com.werewolf.game.WerewolfPlugin;

public class GameManager {
    private final WerewolfPlugin plugin;

    public GameManager(WerewolfPlugin plugin) {
        this.plugin = plugin;
    }

    public WerewolfPlugin getPlugin() {
        return this.plugin;
    }
}
