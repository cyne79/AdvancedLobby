package de.cyne.advancedlobby.listener;

import de.cyne.advancedlobby.AdvancedLobby;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class LeavesDecayListener implements Listener {

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent e) {
        if (!AdvancedLobby.singleWorld_mode | e.getBlock().getWorld() == AdvancedLobby.lobbyWorld) {
            e.setCancelled(true);
        }
    }

}
