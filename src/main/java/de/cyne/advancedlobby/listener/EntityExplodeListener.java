package de.cyne.advancedlobby.listener;

import de.cyne.advancedlobby.AdvancedLobby;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (!AdvancedLobby.singleWorld_mode | e.getEntity().getWorld() == AdvancedLobby.lobbyWorld) {
            e.setCancelled(true);
            e.blockList().clear();
        }
    }

}
