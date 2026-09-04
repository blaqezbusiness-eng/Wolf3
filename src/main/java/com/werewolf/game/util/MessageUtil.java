/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package com.werewolf.game.util;

import com.werewolf.game.WerewolfPlugin;
import com.werewolf.game.util.ColorUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class MessageUtil {
    private final WerewolfPlugin plugin;
    private File messageFile;
    private FileConfiguration messageConfig;

    public MessageUtil(WerewolfPlugin plugin) {
        this.plugin = plugin;
        this.loadMessageFile();
    }

    private void loadMessageFile() {
        this.messageFile = new File(this.plugin.getDataFolder(), "message.yml");
        if (!this.messageFile.exists()) {
            this.plugin.saveResource("message.yml", false);
        }
        this.messageConfig = YamlConfiguration.loadConfiguration((File)this.messageFile);
    }

    public void reload() {
        this.loadMessageFile();
    }

    public String get(String path, Map<String, String> placeholders) {
        String message = this.messageConfig.getString(path, "");
        if (placeholders != null) {
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                message = message.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }
        return ColorUtil.color(message);
    }

    public String get(String path) {
        return this.get(path, null);
    }

    public String raw(String path, Map<String, String> placeholders) {
        String message = this.messageConfig.getString(path, "");
        if (placeholders != null) {
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                message = message.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }
        return message;
    }

    public String raw(String path) {
        return this.raw(path, null);
    }

    public String prefixed(String path, Map<String, String> placeholders) {
        return this.plugin.prefix() + this.get(path, placeholders);
    }

    public String prefixed(String path) {
        return this.prefixed(path, null);
    }

    public String getRoleName(String roleKey) {
        return this.get("roles." + roleKey + ".name");
    }

    public String getRoleDescription(String roleKey) {
        return this.get("roles." + roleKey + ".description");
    }

    public String getRoleNightStart(String roleKey) {
        return this.get("roles." + roleKey + ".night-start");
    }

    public String getRoleNightStart2(String roleKey) {
        return this.get("roles." + roleKey + ".night-start-2");
    }

    public String getRoleDayStart(String roleKey) {
        return this.get("roles." + roleKey + ".day-start");
    }

    public static Map<String, String> ph(Object ... pairs) {
        HashMap<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < pairs.length; i += 2) {
            map.put((String)pairs[i], pairs[i + 1].toString());
        }
        return map;
    }
}
