package de.cyne.advancedlobby.listener;

import de.cyne.advancedlobby.AdvancedLobby;
import de.cyne.advancedlobby.cosmetics.Cosmetics;
import de.cyne.advancedlobby.cosmetics.Cosmetics.ParticleType;
import de.cyne.advancedlobby.misc.HiderType;
import de.cyne.advancedlobby.misc.LocationManager;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (AdvancedLobby.bungeecord | p.getWorld() == AdvancedLobby.lobbyWorld) {
            /*
             * PARTICLES >
             */
            if (Cosmetics.particles.containsKey(p)) {
                if (Cosmetics.particles.get(p) == ParticleType.HEART) {
                    showParticles(p, Effect.HEART, 1, 1, 0.3F, 0.3F, 0.3F, 1.0F, 2, 8);
                }
                if (Cosmetics.particles.get(p) == ParticleType.MUSIC) {
                    showParticles(p, Effect.NOTE, 1, 1, 0.3F, 0.3F, 0.3F, 1.0F, 3, 8);
                }
                if (Cosmetics.particles.get(p) == ParticleType.FLAMES) {
                    showParticles(p, Effect.FLAME, 1, 1, 0.0F, 0.0F, 0.0F, 0.1F, 4, 16);
                }
                if (Cosmetics.particles.get(p) == ParticleType.VILLAGER) {
                    showParticles(p, Effect.HAPPY_VILLAGER, 1, 1, 0.5F, 0.5F, 0.5F, 1.0F, 4, 8);
                }
                if (Cosmetics.particles.get(p) == ParticleType.RAINBOW) {
                    showParticles(p, Effect.COLOURED_DUST, 1, 1, 0.5F, 0.5F, 0.5F, 1.0F, 8, 8);
                }
            }
            /*
             * PARTICLES <
             */

            /*
             * VOID TELEPORT >
             */
            if (AdvancedLobby.cfg.getBoolean("void_teleport.enabled")) {
                if (p.getLocation().getY() < AdvancedLobby.cfg.getDouble("void_teleport.height")) {
                    Location location = LocationManager.getLocation(
                            AdvancedLobby.cfg.getString("spawn_location"));
                    if (location != null) {
                        p.teleport(location);
                    }
                }
            }
            /*
             * VOID TELEPORT <
             */

            /*
             * JUMPPADS >
             */
            if (p.getLocation().getBlock().getType() == Material.GOLD_PLATE | p.getLocation().getBlock().getType() == Material.IRON_PLATE | p.getLocation().getBlock().getType() == Material.STONE_PLATE | p.getLocation().getBlock().getType() == Material.WOOD_PLATE && p.getLocation().subtract(0.0D, 2.0D, 0.0D).getBlock().getType() == Material.REDSTONE_BLOCK) {
                if (AdvancedLobby.cfg.getBoolean("jumppads.enabled")) {
                    Vector vector = p.getLocation().getDirection().multiply(AdvancedLobby.cfg.getDouble("jumppads.lenght")).setY(AdvancedLobby.cfg.getDouble("jumppads.height"));
                    p.setVelocity(vector);

                    AdvancedLobby.playSound(p, p.getLocation(), "jumppads");
                    p.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);

                    for (Player players : Bukkit.getOnlinePlayers()) {
                        if (p != players) {
                            if (!AdvancedLobby.silentlobby.contains(p) && !AdvancedLobby.silentlobby.contains(players) && !AdvancedLobby.playerHider.containsKey(players)) {
                                AdvancedLobby.playSound(players, p.getLocation(), "jumppads");
                                players.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
                            }
                        }
                    }

                }
            }
            /*
             * JUMPPADS <
             */

            /*
             * SHIELD (PLAYER MOVES) >
             */
            if (AdvancedLobby.shield.contains(p)) {
                for (Entity entities : p.getNearbyEntities(2.5D, 2.5D, 2.5D)) {
                    if (entities instanceof Player) {
                        Player nearbyPlayers = (Player) entities;
                        if (!nearbyPlayers.hasMetadata("NPC") && !AdvancedLobby.silentlobby.contains(p) && !AdvancedLobby.silentlobby.contains(nearbyPlayers)) {
                            if (!nearbyPlayers.hasPermission("advancedlobby.shield.bypass")) {

                                Vector nPV = nearbyPlayers.getLocation().toVector();
                                Vector pV = p.getLocation().toVector();
                                Vector v = nPV.clone().subtract(pV).normalize().multiply(0.5D).setY(0.25D);
                                nearbyPlayers.setVelocity(v);

                                p.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 1);

                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    if (p != players) {
                                        if (!AdvancedLobby.silentlobby.contains(p) && !AdvancedLobby.silentlobby.contains(players)) {
                                            if (!nearbyPlayers.hasPermission("advancedlobby.shield.bypass")) {
                                                players.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 1);
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }

                }
            }
            /*
             * SHIELD (PLAYER MOVES) <
             */

            /*
             * SHIELD (NEARBY PLAYER MOVES) >
             */
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (p != players) {
                    if (AdvancedLobby.shield.contains(players)) {
                        if (!p.hasMetadata("NPC") && !AdvancedLobby.silentlobby.contains(p) && !AdvancedLobby.silentlobby.contains(players)) {
                            if (p.getLocation().distance(players.getLocation()) <= 2.5) {


                                Vector pV = p.getLocation().toVector();
                                Vector pSV = players.getLocation().toVector();
                                Vector v = pV.clone().subtract(pSV).normalize().multiply(0.5D).setY(0.25D);


                                p.setVelocity(v);

                                p.playEffect(players.getLocation(), Effect.ENDER_SIGNAL, 1);
                                players.playEffect(players.getLocation(), Effect.ENDER_SIGNAL, 1);

                                for (Player players1 : Bukkit.getOnlinePlayers()) {
                                    if (p != players1) {
                                        if (!AdvancedLobby.silentlobby.contains(p) && !AdvancedLobby.silentlobby.contains(players)) {
                                            if (!p.hasPermission("advancedlobby.shield.bypass")) {
                                                players1.playEffect(players.getLocation(), Effect.ENDER_SIGNAL, 1);
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
            /*
             * SHIELD (NEARBY PLAYER MOVES) <
             */

            /*
             * WORLDBORDER >
             */
            if (AdvancedLobby.cfg.getBoolean("worldborder.enabled")) {
                Location center_location = LocationManager.getLocation(
                        AdvancedLobby.cfg.getString("worldborder.center_location"));
                if (center_location != null) {
                    if (playerAtWorldBorder(p, center_location)) {
                        if (!AdvancedLobby.build.contains(p)) {

                            Vector lV = center_location.toVector();
                            Vector pV = p.getLocation().toVector();
                            Vector v = lV.clone().subtract(pV).normalize().multiply(0.5D).setY(0.25D);

                            p.setVelocity(v);
                            p.playEffect(p.getLocation(), Effect.SMOKE, 1);
                            AdvancedLobby.playSound(p, p.getLocation(), "worldborder_push_back");

                            for (Player players : Bukkit.getOnlinePlayers()) {
                                if (p != players) {
                                    if (!AdvancedLobby.silentlobby.contains(players) && !AdvancedLobby.silentlobby.contains(p)) {
                                        if (!AdvancedLobby.playerHider.containsKey(players)) {
                                            players.playEffect(p.getLocation(), Effect.SMOKE, 1);
                                            AdvancedLobby.playSound(players, p.getLocation(), "worldborder_push_back");
                                        }
                                    }
                                }
                            }

                            Bukkit.getScheduler().scheduleSyncDelayedTask(AdvancedLobby.getInstance(), () -> {
                                if (playerAtWorldBorder(p, center_location)) {
                                    if (!AdvancedLobby.build.contains(p)) {
                                        Location location = LocationManager.getLocation(
                                                AdvancedLobby.cfg.getString("spawn_location"));
                                        if (location != null) {
                                            p.teleport(location);
                                        }
                                    }
                                }
                            }, 100L);

                        }
                    }
                }
            }
            /*
             * WORLDBORDER <
             */
        }

    }


    private void showParticles(Player player, Effect effect, int id, int data, float offsetX, float offsetY, float offsetZ, float speed, int particleCount, int radius) {
        player.spigot().playEffect(player.getLocation(), effect, id, data, offsetX, offsetY, offsetZ, speed, particleCount, radius);
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players != player && !AdvancedLobby.silentlobby.contains(players) && !AdvancedLobby.silentlobby.contains(player) && !(AdvancedLobby.playerHider.get(players) == HiderType.NONE)) {
                players.spigot().playEffect(player.getLocation(), effect, id, data, offsetX, offsetY, offsetZ, speed, particleCount, radius);
            }
        }
    }

    private boolean playerAtWorldBorder(Player player, Location location) {
        double radius = AdvancedLobby.cfg.getDouble("worldborder.radius");
        if (player.getLocation().getX() > location.getX() + radius | player.getLocation().getX() < location.getX() - radius | player.getLocation().getZ() > location.getZ() + radius | player.getLocation().getZ() < location.getZ() - radius) {
            return true;
        }
        return false;
    }

}
