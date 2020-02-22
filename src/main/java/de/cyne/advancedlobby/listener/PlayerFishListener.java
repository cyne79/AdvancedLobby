package de.cyne.advancedlobby.listener;

import de.cyne.advancedlobby.AdvancedLobby;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

public class PlayerFishListener implements Listener {

    @EventHandler
    public void onPlayerFish(PlayerFishEvent e) {
        Player p = e.getPlayer();
        FishHook hook = e.getHook();


        if (p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().getDisplayName().equals(AdvancedLobby.getString("hotbar_items.gadget.equipped.displayname").replace("%gadget%", AdvancedLobby.getString("inventories.cosmetics_gadgets.grappling_hook_gadget.displayname")))) {
            if (AdvancedLobby.bungeecord | p.getWorld() == AdvancedLobby.lobbyWorld) {
                if (!AdvancedLobby.build.contains(p)) {
                    if (hook.getLocation().subtract(0, 1, 0).getBlock().getType() != Material.AIR) {
                        Vector pV = p.getLocation().toVector();
                        Vector hV = hook.getLocation().toVector();
                        Vector v = hV.clone().subtract(pV).normalize().multiply(1.5D).setY(0.5D);

                        p.setVelocity(v);
                        AdvancedLobby.playSound(p, p.getLocation(), "gadgets.grappling_hook");
                        for (Player players : Bukkit.getOnlinePlayers()) {
                            if (p != players) {
                                if (!AdvancedLobby.silentlobby.contains(p) && !AdvancedLobby.silentlobby.contains(players) && !AdvancedLobby.playerHider.containsKey(players)) {
                                    AdvancedLobby.playSound(players, p.getLocation(), "gadgets.grappling_hook");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
