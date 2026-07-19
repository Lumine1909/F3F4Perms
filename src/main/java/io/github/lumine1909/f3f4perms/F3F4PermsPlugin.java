package io.github.lumine1909.f3f4perms;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.protocol.player.GameMode;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityStatus;
import io.github.lumine1909.f3f4perms.metrics.Metrics;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class F3F4PermsPlugin extends JavaPlugin {

    private static final Map<Player, Integer> currentLevel = new ConcurrentHashMap<>();
    public static F3F4PermsPlugin plugin;
    private static Metrics metrics;

    @Override
    public void onLoad() {
        plugin = this;
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {
        PacketEvents.getAPI().init();
        Bukkit.getPluginManager().registerEvents(new F3F4PlayerListener(), this);
        PacketEvents.getAPI().getEventManager().registerListener(new F3F4PacketListener(), PacketListenerPriority.HIGHEST);
        LuckPermsHook.register(this);
        metrics = new Metrics(this, 27254);
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
        LuckPermsHook.unregister();
        metrics.shutdown();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!isAdmin(sender)) {
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("forceupdate")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                updateOpLevel(player, true);
            }
            sender.sendMessage(ChatColor.GREEN + "[F3F4Perms] Successfully update player's F3+F4 permission");
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return args.length <= 1 ? Collections.singletonList("forceupdate") : Collections.emptyList();
    }

    public void updateOpLevel(Player player, boolean force) {
        if (PacketEvents.getAPI().getPlayerManager().getChannel(player) == null) {
            return;
        }
        int status = canUse(player) ? 28 : 24;
        if (!force && Objects.equals(currentLevel.get(player), status)) {
            return;
        }
        updateRecord(player, status);
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, new WrapperPlayServerEntityStatus(player.getEntityId(), status));
    }

    public void updateRecord(Player player, int level) {
        currentLevel.put(player, level);
    }

    public void changeGameMode(Player player, GameMode gameMode) {
        String command = "/gamemode " + gameMode.toString().toLowerCase();
        player.chat(command);
    }

    public boolean canUse(Player player) {
        return player.isOp() || player.hasPermission("f3f4perms.use") || player.hasPermission("f3nperm.use");
    }

    public boolean isAdmin(CommandSender sender) {
        return sender.isOp() || sender.hasPermission("f3f4perms.admin") || sender.hasPermission("f3nperm.admin");
    }

    public void invalidate(Player player) {
        currentLevel.remove(player);
    }
}
