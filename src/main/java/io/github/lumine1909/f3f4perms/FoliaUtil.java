package io.github.lumine1909.f3f4perms;

import io.github.retrooper.packetevents.util.folia.FoliaScheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FoliaUtil {

    public static void runTask(Plugin plugin, Player player, Runnable runnable) {
        if (player == null || runnable == null) {
            return;
        }
        FoliaScheduler.getRegionScheduler().run(plugin, player.getLocation(), task -> runnable.run());
    }

    public static void runTaskLater(Plugin plugin, Player player, Runnable runnable, long delay) {
        if (player == null || runnable == null) {
            return;
        }
        FoliaScheduler.getRegionScheduler().runDelayed(plugin, player.getLocation(), task -> runnable.run(), delay);
    }
}
