package io.github.lumine1909.f3f4perms;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientChangeGameMode;
import org.bukkit.Bukkit;

import static io.github.lumine1909.f3f4perms.F3F4PermsPlugin.plugin;

public class F3F4PacketListener implements PacketListener {

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() != PacketType.Play.Client.CHANGE_GAME_MODE) {
            return;
        }
        if (!plugin.canUse(event.getPlayer())) {
            return;
        }
        WrapperPlayClientChangeGameMode gameModePacket = new WrapperPlayClientChangeGameMode(event);
        event.setCancelled(true);
        Bukkit.getScheduler().runTask(plugin, () -> plugin.changeGameMode(event.getPlayer(), gameModePacket.getGameMode()));
    }
}