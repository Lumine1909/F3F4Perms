package io.github.lumine1909.f3f4perms;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.event.EventSubscription;
import net.luckperms.api.event.node.NodeMutateEvent;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LuckPermsHook {

    private static EventSubscription<?> subscription;

    public static void register(F3F4PermsPlugin plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("LuckPerms") == null) {
            return;
        }
        LuckPerms luckPerms = LuckPermsProvider.get();
        subscription = luckPerms.getEventBus().subscribe(NodeMutateEvent.class, e -> {
            if (e.getTarget() instanceof User) {
                Player player = Bukkit.getPlayer(((User) e.getTarget()).getUniqueId());
                FoliaUtil.runTaskLater(plugin, player, () -> plugin.updateOpLevel(player, false), 1L);
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