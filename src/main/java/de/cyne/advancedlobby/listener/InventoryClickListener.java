package de.cyne.advancedlobby.listener;

import de.cyne.advancedlobby.AdvancedLobby;
import de.cyne.advancedlobby.cosmetics.Cosmetics;
import de.cyne.advancedlobby.cosmetics.Cosmetics.ParticleType;
import de.cyne.advancedlobby.inventories.Inventories;
import de.cyne.advancedlobby.itembuilder.ItemBuilder;
import de.cyne.advancedlobby.locale.Locale;
import de.cyne.advancedlobby.misc.LocationManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (AdvancedLobby.bungeecord | p.getWorld() == AdvancedLobby.lobbyWorld) {
            if (e.getCurrentItem() != null) {
                if (e.getClickedInventory() == p.getInventory() && !AdvancedLobby.build.contains(p)) {
                    e.setCancelled(true);
                }
                /*
                 * COMPASS >
                 */
                if (e.getInventory().getTitle().equals(AdvancedLobby.getString("inventories.teleporter.title"))) {
                    e.setCancelled(true);

                    for (String item : AdvancedLobby.cfg.getConfigurationSection("inventories.teleporter.items").getKeys(false)) {
                        Material material = Material
                                .getMaterial(AdvancedLobby.cfg.getString("inventories.teleporter.items." + item + ".material"));
                        if (e.getCurrentItem().getType() == material) {

                            Location location = LocationManager.getLocation(
                                    AdvancedLobby.cfg.getString("inventories.teleporter.items." + item + ".location"));
                            if (location == null) {
                                p.closeInventory();
                                if (p.hasPermission("advancedlobby.admin")) {
                                    p.sendMessage(Locale.COMPASS_LOC_NOT_FOUND_ADMIN.getMessage(p).replace("%location%",
                                            AdvancedLobby.cfg.getString("inventories.teleporter.items." + item + ".location")));
                                    return;
                                }
                                p.sendMessage(Locale.COMPASS_LOC_NOT_FOUND.getMessage(p).replace("%location%",
                                        AdvancedLobby.cfg.getString("inventories.teleporter.items." + item + ".location")));
                                return;
                            }
                            p.teleport(location);
                            AdvancedLobby.playSound(p, p.getLocation(), "teleporter.teleport");
                            p.spigot().playEffect(location, Effect.WITCH_MAGIC, 1, 1, 0, 0, 0, 0.1f, 64, 8);
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                if (p != players) {
                                    if (!AdvancedLobby.playerHider.containsKey(players) && !AdvancedLobby.silentlobby.contains(players) && !AdvancedLobby.silentlobby.contains(p)) {
                                        players.spigot().playEffect(location, Effect.WITCH_MAGIC, 1, 1, 0, 0, 0, 0.1f, 64, 8);
                                    }
                                }
                            }
                        }
                    }

                }
                /*
                 * COMPASS <
                 */

                /*
                 * COSMETICS >
                 */
                if (e.getInventory().getTitle().equals(AdvancedLobby.getString("inventories.cosmetics.title"))) {
                    e.setCancelled(true);

                    if (e.getCurrentItem().getType() == Material.PUMPKIN) {
                        Inventories.openCosmetics_hats(p);
                        AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.change_page");
                        return;
                    }

                    if (e.getCurrentItem().getType() == Material.BLAZE_POWDER) {
                        Inventories.openCosmetics_particles(p);
                        AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.change_page");
                        return;
                    }

                    if (e.getCurrentItem().getType() == Material.LEASH) {
                        Inventories.openCosmetics_balloons(p);
                        AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.change_page");
                        return;
                    }

                    if (e.getCurrentItem().getType() == Material.FISHING_ROD) {
                        Inventories.openCosmetics_gadgets(p);
                        AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.change_page");
                        return;
                    }

                    if (e.getCurrentItem().getType() == Material.IRON_BOOTS) {
                        Inventories.openCosmetics_wardrobe(p);
                        AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.change_page");
                        return;
                    }

                }
                /*
                 * COSMETICS <
                 */

                /*
                 * COSMETICS - HATS >
                 */
                if (e.getInventory().getTitle().equals(AdvancedLobby.getString("inventories.cosmetics_hats.title"))) {
                    e.setCancelled(true);
                    if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
                        Inventories.openCosmetics(p);
                        AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.change_page");
                        return;
                    }

                    if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) return;
                    if (e.getCurrentItem().getType() == Material.AIR) return;

                    if (e.getCurrentItem().getType() == Material.INK_SACK) {
                        AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.disable_cosmetic");
                        p.closeInventory();
                        if (Cosmetics.hats.containsKey(p)) {
                            Cosmetics.hats.remove(p);
                            p.getInventory().setHelmet(null);
                            p.sendMessage(Locale.COSMETICS_HATS_DISABLE.getMessage(p));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_HATS_DISABLE_ERROR.getMessage(p));
                        return;
                    }
                    p.closeInventory();
                    AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.equip_cosmetic");
                    Cosmetics.equipHat(p, Cosmetics.HatType.valueOf(e.getCurrentItem().getType().toString()));
                    return;

                }
                /*
                 * COSMETICS - HATS <
                 */

                /*
                 * COSMETICS - PARTICLES >
                 */
                if (e.getInventory().getTitle().equals(AdvancedLobby.getString("inventories.cosmetics_particles.title"))) {
                    e.setCancelled(true);

                    if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
                        Inventories.openCosmetics(p);
                        AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.change_page");
                        return;
                    }

                    if (e.getCurrentItem().getType() == Material.INK_SACK) {
                        p.closeInventory();
                        AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.disable_cosmetic");
                        if (Cosmetics.particles.containsKey(p)) {
                            Cosmetics.particles.remove(p);
                            p.sendMessage(Locale.COSMETICS_PARTICLES_DISABLE.getMessage(p));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_PARTICLES_DISABLE_ERROR.getMessage(p));
                        return;
                    }

                    if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) return;
                    if (e.getCurrentItem().getType() == Material.AIR) return;

                    AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.equip_cosmetic");
                    if (e.getCurrentItem().getType() == Material.REDSTONE) {
                        p.closeInventory();
                        if (p.hasPermission("advancedlobby.cosmetics.particles.heart")) {
                            Cosmetics.particles.put(p, ParticleType.HEART);
                            p.sendMessage(Locale.COSMETICS_PARTICLES_EQUIP.getMessage(p).replace("%particles%", AdvancedLobby.getString("inventories.cosmetics_particles.heart_particles.displayname")));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_PARTICLES_NO_PERMISSION.getMessage(p).replace("%particles%", AdvancedLobby.getString("inventories.cosmetics_particles.heart_particles.displayname")));
                        return;
                    }
                    if (e.getCurrentItem().getType() == Material.RECORD_10) {
                        p.closeInventory();
                        if (p.hasPermission("advancedlobby.cosmetics.particles.music")) {
                            Cosmetics.particles.put(p, ParticleType.MUSIC);
                            p.sendMessage(Locale.COSMETICS_PARTICLES_EQUIP.getMessage(p).replace("%particles%", AdvancedLobby.getString("inventories.cosmetics_particles.music_particles.displayname")));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_PARTICLES_NO_PERMISSION.getMessage(p).replace("%particles%", AdvancedLobby.getString("inventories.cosmetics_particles.music_particles.displayname")));
                        return;
                    }
                    if (e.getCurrentItem().getType() == Material.FIREBALL) {
                        p.closeInventory();
                        if (p.hasPermission("advancedlobby.cosmetics.particles.flames")) {
                            Cosmetics.particles.put(p, ParticleType.FLAMES);
                            p.sendMessage(Locale.COSMETICS_PARTICLES_EQUIP.getMessage(p).replace("%particles%", AdvancedLobby.getString("inventories.cosmetics_particles.flames_particles.displayname")));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_PARTICLES_NO_PERMISSION.getMessage(p).replace("%particles%", AdvancedLobby.getString("inventories.cosmetics_particles.flames_particles.displayname")));
                        return;
                    }
                    if (e.getCurrentItem().getType() == Material.EMERALD) {
                        p.closeInventory();
                        if (p.hasPermission("advancedlobby.cosmetics.particles.villager")) {
                            Cosmetics.particles.put(p, ParticleType.VILLAGER);
                            p.sendMessage(Locale.COSMETICS_PARTICLES_EQUIP.getMessage(p).replace("%particles%", AdvancedLobby.getString("inventories.cosmetics_particles.villager_particles.displayname")));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_PARTICLES_NO_PERMISSION.getMessage(p).replace("%particles%", AdvancedLobby.getString("inventories.cosmetics_particles.villager_particles.displayname")));
                        return;
                    }
                    if (e.getCurrentItem().getType() == Material.NETHER_STAR) {
                        p.closeInventory();
                        if (p.hasPermission("advancedlobby.cosmetics.particles.rainbow")) {
                            Cosmetics.particles.put(p, ParticleType.RAINBOW);
                            p.sendMessage(Locale.COSMETICS_PARTICLES_EQUIP.getMessage(p).replace("%particles%", AdvancedLobby.getString("inventories.cosmetics_particles.rainbow_particles.displayname")));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_PARTICLES_NO_PERMISSION.getMessage(p).replace("%particles%", AdvancedLobby.getString("inventories.cosmetics_particles.rainbow_particles.displayname")));
                        return;
                    }
                }
                /*
                 * COSMETICS - PARTICLES <
                 */

                /*
                 * COSMETICS - BALLOONS >
                 */
                if (e.getInventory().getTitle().equals(AdvancedLobby.getString("inventories.cosmetics_balloons.title"))) {
                    e.setCancelled(true);

                    if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
                        Inventories.openCosmetics(p);
                        AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.change_page");
                        return;
                    }

                    if (e.getCurrentItem().getType() == Material.INK_SACK) {
                        p.closeInventory();
                        AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.disable_cosmetic");
                        if (Cosmetics.balloons.containsKey(p)) {
                            p.sendMessage(Locale.COSMETICS_BALLOONS_DISABLE.getMessage(p));
                            Cosmetics.balloons.get(p).remove();
                            Cosmetics.balloons.remove(p);
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_BALLOONS_DISABLE_ERROR.getMessage(p));
                        return;
                    }

                    if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) return;
                    if (e.getCurrentItem().getType() == Material.AIR) return;


                    if (Cosmetics.balloons.containsKey(p)) {
                        Cosmetics.balloons.get(p).remove();
                        Cosmetics.balloons.remove(p);
                    }

                    AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.equip_cosmetic");

                    //yellow clay, byte 4
                    if (e.getCurrentItem().getType() == Material.STAINED_CLAY && e.getCurrentItem().getDurability() == 4) {
                        p.closeInventory();
                        if (p.hasPermission("advancedlobby.cosmetics.balloons.yellow")) {
                            Cosmetics.equipBalloon(p, Cosmetics.BalloonType.YELLOW);
                            p.sendMessage(Locale.COSMETICS_BALLOONS_EQUIP.getMessage(p).replace("%balloon%", AdvancedLobby.getString("inventories.cosmetics_balloons.yellow_balloon.displayname")));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_BALLOONS_NO_PERMISSION.getMessage(p).replace("%balloon%", AdvancedLobby.getString("inventories.cosmetics_balloons.yellow_balloon.displayname")));
                        return;
                    }
                    //red clay, byte 14
                    if (e.getCurrentItem().getType() == Material.STAINED_CLAY && e.getCurrentItem().getDurability() == 14) {
                        p.closeInventory();
                        if (p.hasPermission("advancedlobby.cosmetics.balloons.red")) {
                            Cosmetics.equipBalloon(p, Cosmetics.BalloonType.RED);
                            p.sendMessage(Locale.COSMETICS_BALLOONS_EQUIP.getMessage(p).replace("%balloon%", AdvancedLobby.getString("inventories.cosmetics_balloons.red_balloon.displayname")));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_BALLOONS_NO_PERMISSION.getMessage(p).replace("%balloon%", AdvancedLobby.getString("inventories.cosmetics_balloons.red_balloon.displayname")));
                        return;
                    }
                    //green clay, byte 5
                    if (e.getCurrentItem().getType() == Material.STAINED_CLAY && e.getCurrentItem().getDurability() == 5) {
                        p.closeInventory();
                        if (p.hasPermission("advancedlobby.cosmetics.balloons.green")) {
                            Cosmetics.equipBalloon(p, Cosmetics.BalloonType.GREEN);
                            p.sendMessage(Locale.COSMETICS_BALLOONS_EQUIP.getMessage(p).replace("%balloon%", AdvancedLobby.getString("inventories.cosmetics_balloons.green_balloon.displayname")));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_BALLOONS_NO_PERMISSION.getMessage(p).replace("%balloon%", AdvancedLobby.getString("inventories.cosmetics_balloons.green_balloon.displayname")));
                        return;
                    }
                    //blue clay, byte 3
                    if (e.getCurrentItem().getType() == Material.STAINED_CLAY && e.getCurrentItem().getDurability() == 3) {
                        p.closeInventory();
                        if (p.hasPermission("advancedlobby.cosmetics.balloons.blue")) {
                            Cosmetics.equipBalloon(p, Cosmetics.BalloonType.BLUE);
                            p.sendMessage(Locale.COSMETICS_BALLOONS_EQUIP.getMessage(p).replace("%balloon%", AdvancedLobby.getString("inventories.cosmetics_balloons.blue_balloon.displayname")));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_BALLOONS_NO_PERMISSION.getMessage(p).replace("%balloon%", AdvancedLobby.getString("inventories.cosmetics_balloons.blue_balloon.displayname")));
                        return;
                    }
                    //hay
                    if (e.getCurrentItem().getType() == Material.HAY_BLOCK) {
                        p.closeInventory();
                        if (p.hasPermission("advancedlobby.cosmetics.balloons.hay_block")) {
                            Cosmetics.equipBalloon(p, Cosmetics.BalloonType.HAY_BLOCK);
                            p.sendMessage(Locale.COSMETICS_BALLOONS_EQUIP.getMessage(p).replace("%balloon%", AdvancedLobby.getString("inventories.cosmetics_balloons.hay_block_balloon.displayname")));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_BALLOONS_NO_PERMISSION.getMessage(p).replace("%balloon%", AdvancedLobby.getString("inventories.cosmetics_balloons.hay_block_balloon.displayname")));
                        return;
                    }
                    //sea lantern
                    if (e.getCurrentItem().getType() == Material.SEA_LANTERN) {
                        p.closeInventory();
                        if (p.hasPermission("advancedlobby.cosmetics.balloons.sea_lantern")) {
                            Cosmetics.equipBalloon(p, Cosmetics.BalloonType.SEA_LANTERN);
                            p.sendMessage(Locale.COSMETICS_BALLOONS_EQUIP.getMessage(p).replace("%balloon%", AdvancedLobby.getString("inventories.cosmetics_balloons.sea_lantern_balloon.displayname")));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_BALLOONS_NO_PERMISSION.getMessage(p).replace("%balloon%", AdvancedLobby.getString("inventories.cosmetics_balloons.sea_lantern_balloon.displayname")));
                        return;
                    }
                    //bookshelf
                    if (e.getCurrentItem().getType() == Material.BOOKSHELF) {
                        p.closeInventory();
                        if (p.hasPermission("advancedlobby.cosmetics.balloons.bookshelf")) {
                            Cosmetics.equipBalloon(p, Cosmetics.BalloonType.BOOKSHELF);
                            p.sendMessage(Locale.COSMETICS_BALLOONS_EQUIP.getMessage(p).replace("%balloon%", AdvancedLobby.getString("inventories.cosmetics_balloons.bookshelf_balloon.displayname")));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_BALLOONS_NO_PERMISSION.getMessage(p).replace("%balloon%", AdvancedLobby.getString("inventories.cosmetics_balloons.bookshelf_balloon.displayname")));
                        return;
                    }
                    //note_block
                    if (e.getCurrentItem().getType() == Material.NOTE_BLOCK) {
                        p.closeInventory();
                        if (p.hasPermission("advancedlobby.cosmetics.balloons.note_block")) {
                            Cosmetics.equipBalloon(p, Cosmetics.BalloonType.NOTE_BLOCK);
                            p.sendMessage(Locale.COSMETICS_BALLOONS_EQUIP.getMessage(p).replace("%balloon%", AdvancedLobby.getString("inventories.cosmetics_balloons.note_block_balloon.displayname")));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_BALLOONS_NO_PERMISSION.getMessage(p).replace("%balloon%", AdvancedLobby.getString("inventories.cosmetics_balloons.note_block_balloon.displayname")));
                        return;
                    }


                }
                /*
                 * COSMETICS - BALLOONS <
                 */

                /*
                 * COSMETICS - GADGETS >
                 */
                if (e.getInventory().getTitle().equals(AdvancedLobby.getString("inventories.cosmetics_gadgets.title"))) {
                    e.setCancelled(true);
                    if (e.getCurrentItem().getType() == Material.SKULL_ITEM) {
                        Inventories.openCosmetics(p);
                        AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.change_page");
                        return;
                    }

                    if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) return;
                    if (e.getCurrentItem().getType() == Material.AIR) return;

                    if (e.getCurrentItem().getType() == Material.INK_SACK) {
                        AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.disable_cosmetic");
                        p.closeInventory();
                        if (Cosmetics.gadgets.containsKey(p)) {
                            Cosmetics.gadgets.remove(p);
                            ItemBuilder no_gadget = new ItemBuilder(Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.gadget.unequipped.material")), 1,
                                    (short) AdvancedLobby.cfg.getInt("hotbar_items.gadget.unequipped.subid")).setDisplayName(
                                    ChatColor.translateAlternateColorCodes('&', AdvancedLobby.cfg.getString("hotbar_items.gadget.unequipped.displayname")))
                                    .setLore(AdvancedLobby.cfg.getStringList("hotbar_items.gadget.unequipped.lore"));
                            p.getInventory().setItem(AdvancedLobby.cfg.getInt("hotbar_items.gadget.slot"), no_gadget);
                            p.sendMessage(Locale.COSMETICS_GADGETS_DISABLE.getMessage(p));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_GADGETS_DISABLE_ERROR.getMessage(p));
                        return;
                    }

                    AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.equip_cosmetic");

                    if (e.getCurrentItem().getType() == Material.FISHING_ROD) {
                        p.closeInventory();
                        if (p.hasPermission("advancedlobby.cosmetics.gadgets.grappling_hook")) {
                            Cosmetics.equipGadget(p, Cosmetics.GadgetType.GRAPPLING_HOOK);
                            p.sendMessage(Locale.COSMETICS_GADGETS_EQUIP.getMessage(p).replace("%gadget%", AdvancedLobby.getString("inventories.cosmetics_gadgets.grappling_hook_gadget.displayname")));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_GADGETS_NO_PERMISSION.getMessage(p).replace("%gadget%", AdvancedLobby.getString("inventories.cosmetics_gadgets.grappling_hook_gadget.displayname")));
                        return;
                    }
                    if (e.getCurrentItem().getType() == Material.FEATHER) {
                        p.closeInventory();
                        if (p.hasPermission("advancedlobby.cosmetics.gadgets.rocket_jump")) {
                            Cosmetics.equipGadget(p, Cosmetics.GadgetType.ROCKET_JUMP);
                            p.sendMessage(Locale.COSMETICS_GADGETS_EQUIP.getMessage(p).replace("%gadget%", AdvancedLobby.getString("inventories.cosmetics_gadgets.rocket_jump_gadget.displayname")));
                            return;
                        }
                        p.sendMessage(Locale.COSMETICS_GADGETS_NO_PERMISSION.getMessage(p).replace("%gadget%", AdvancedLobby.getString("inventories.cosmetics_gadgets.rocket_jump_gadget.displayname")));
                        return;
                    }
                }
                /*
                 * COSMETICS - GADGETS <
                 */
            }
        }
    }

}
