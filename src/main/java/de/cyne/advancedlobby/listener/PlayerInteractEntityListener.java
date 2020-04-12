package de.cyne.advancedlobby.listener;

import de.cyne.advancedlobby.AdvancedLobby;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntityListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if (!AdvancedLobby.singleWorld_mode | p.getWorld() == AdvancedLobby.lobbyWorld) {
            if (e.getRightClicked().getType() == EntityType.ITEM_FRAME && !AdvancedLobby.build.contains(p)) {
                e.setCancelled(true);
            }
            if (p.getItemInHand().getType() == Material.NAME_TAG && !AdvancedLobby.build.contains(p)) {
                e.setCancelled(true);
            }
        }
    }

}
