package de.cyne.advancedlobby.listener;

import de.cyne.advancedlobby.AdvancedLobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (AdvancedLobby.bungeecord | p.getWorld() == AdvancedLobby.lobbyWorld) {
                e.setCancelled(true);
            }
        } else if (AdvancedLobby.cfg.getBoolean("disable_mob_damage")) {
            if (AdvancedLobby.bungeecord | e.getEntity().getWorld() == AdvancedLobby.lobbyWorld) {
                e.setCancelled(true);
            }
        }

    }

}
