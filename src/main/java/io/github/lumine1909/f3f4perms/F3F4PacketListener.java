package io.github.lumine1909.f3f4perms;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientChangeGameMode;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityStatus;
import org.bukkit.entity.Player;

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
        Player player = event.getPlayer();
        FoliaUtil.runTask(plugin, player, () -> plugin.changeGameMode(player, gameModePacket.getGameMode()));
    }

    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.ENTITY_STATUS) {
            return;
        }
        if (event.getPlayer() == null || !plugin.canUse(event.getPlayer())) {
            return;
        }
        WrapperPlayServerEntityStatus entityStatusPacket = new WrapperPlayServerEntityStatus(event);
        if (entityStatusPacket.getEntityId() != ((Player) event.getPlayer()).getEntityId()) {
            return;
        }
        if (entityStatusPacket.getStatus() <= 28 && entityStatusPacket.getStatus() >= 24) {
            entityStatusPacket.setStatus(28);
        }
    }
}