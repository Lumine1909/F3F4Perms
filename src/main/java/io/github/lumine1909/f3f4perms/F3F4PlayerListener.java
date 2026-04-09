package io.github.lumine1909.f3f4perms;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static io.github.lumine1909.f3f4perms.F3F4PermsPlugin.plugin;

public class F3F4PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.updateOpLevel(event.getPlayer(), true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.invalidate(event.getPlayer());
    }
}
