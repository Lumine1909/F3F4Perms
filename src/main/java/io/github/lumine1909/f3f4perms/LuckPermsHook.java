package io.github.lumine1909.f3f4perms;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.event.EventSubscription;
import net.luckperms.api.event.context.ContextUpdateEvent;
import org.bukkit.entity.Player;

public class LuckPermsHook {

    private static EventSubscription<?> subscription;

    public static void register(F3F4PermsPlugin plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") == null) {
            return;
        }
        LuckPerms luckPerms = LuckPermsProvider.get();
        subscription = luckPerms.getEventBus().subscribe(ContextUpdateEvent.class, e -> {
            if (e.getSubject() instanceof Player) {
                plugin.updateOpLevel((Player) e.getSubject(), false);
            }
        });
        plugin.getLogger().info("Successfully hooked into LuckPerms!");
    }

    public static void unregister() {
        if (subscription != null) {
            subscription.close();
        }
    }
}