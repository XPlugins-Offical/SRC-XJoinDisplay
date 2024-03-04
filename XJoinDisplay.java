package com.example.xjoindisplay;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class XJoinDisplay extends JavaPlugin implements Listener {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        config = getConfig();

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void saveDefaultConfig() {
        File configFile = new File(getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
    }

    public FileConfiguration getCustomConfig() {
        return config;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        boolean displayEnabled = getCustomConfig().getBoolean("display", true);
        if (displayEnabled) {
            String displayMessage = getCustomConfig().getString("displayMessage", "");

            sendTitle(player, displayMessage);
        }

        boolean chatEnabled = getCustomConfig().getBoolean("chat", true);
        if (chatEnabled) {
            String chatMessage = getCustomConfig().getString("chatMessage", "");

            player.sendMessage(chatMessage);
        }
    }

    private void sendTitle(Player player, String message) {
        player.sendTitle(message, "", 2, 14, 4);

        new BukkitRunnable() {
            @Override
            public void run() {
                player.resetTitle();
            }
        }.runTaskLater(this, 60);
    }
}