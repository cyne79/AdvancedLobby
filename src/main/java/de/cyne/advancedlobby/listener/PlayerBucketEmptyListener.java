package de.cyne.advancedlobby.listener;

import de.cyne.advancedlobby.AdvancedLobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class PlayerBucketEmptyListener implements Listener {

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e) {
        Player p = e.getPlayer();
        if (!AdvancedLobby.multiWorld_mode | AdvancedLobby.lobbyWorlds.contains(p.getWorld())) {
            if (!AdvancedLobby.build.contains(p)) {
                e.setCancelled(true);
            }
        }
    }

}
