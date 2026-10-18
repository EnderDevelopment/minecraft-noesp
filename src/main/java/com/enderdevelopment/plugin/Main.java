package com.enderdevelopment.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.User;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.World;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("AntiFreecam plugin enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("AntiFreecam plugin disabled");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if (to.getY() > 16 && from.getY() <= 16) {
            // Player moved above y=16
            hideBelowY0(player);
        } else if (to.getY() <= 16 && from.getY() > 16) {
            // Player moved below or to y=16
            showAll(player);
        }
    }

    private void hideBelowY0(Player player) {
        World world = player.getWorld();
        int chunkX = player.getLocation().getChunk().getX();
        int chunkZ = player.getLocation().getChunk().getZ();

        // Hide chunks below y=0 within 4-chunk radius
        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
                world.refreshChunk(chunkX + x, chunkZ + z);
            }
        }
    }

    private void showAll(Player player) {
        World world = player.getWorld();
        int chunkX = player.getLocation().getChunk().getX();
        int chunkZ = player.getLocation().getChunk().getZ();

        // Show all chunks within 4-chunk radius
        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
                world.refreshChunk(chunkX + x, chunkZ + z);
            }
        }
    }
}