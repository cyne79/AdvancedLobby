package de.cyne.advancedlobby.listener;

import de.cyne.advancedlobby.AdvancedLobby;
import de.cyne.advancedlobby.cosmetics.Cosmetics;
import de.cyne.advancedlobby.inventories.Inventories;
import de.cyne.advancedlobby.itembuilder.ItemBuilder;
import de.cyne.advancedlobby.locale.Locale;
import de.cyne.advancedlobby.misc.HiderType;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class PlayerInteractListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();

        if (AdvancedLobby.bungeecord | p.getWorld() == AdvancedLobby.lobbyWorld) {
            if (e.getAction() == Action.PHYSICAL) {
                if (AdvancedLobby.cfg.getBoolean("disable_physical_player_interaction")) {
                    if (!AdvancedLobby.build.contains(p)) e.setCancelled(true);
                }
            }

            if (e.getAction() == Action.LEFT_CLICK_BLOCK | e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (e.getClickedBlock().getType() == Material.CHEST | e.getClickedBlock().getType() == Material.ENDER_CHEST | e.getClickedBlock().getType() == Material.TRAPPED_CHEST | e.getClickedBlock().getType() == Material.WORKBENCH | e.getClickedBlock().getType() == Material.FURNACE | e.getClickedBlock().getType() == Material.ENDER_CHEST | e.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE | e.getClickedBlock().getType() == Material.ANVIL | e.getClickedBlock().getType() == Material.BED_BLOCK | e.getClickedBlock().getType() == Material.JUKEBOX | e.getClickedBlock().getType() == Material.BEACON | e.getClickedBlock().getType() == Material.DISPENSER | e.getClickedBlock().getType() == Material.LEVER | e.getClickedBlock().getType() == Material.STONE_BUTTON | e.getClickedBlock().getType() == Material.WOOD_BUTTON | e.getClickedBlock().getType() == Material.DAYLIGHT_DETECTOR | e.getClickedBlock().getType() == Material.DAYLIGHT_DETECTOR_INVERTED | e.getClickedBlock().getType() == Material.HOPPER | e.getClickedBlock().getType() == Material.DROPPER | e.getClickedBlock().getType() == Material.BREWING_STAND | e.getClickedBlock().getType() == Material.REDSTONE_COMPARATOR_OFF | e.getClickedBlock().getType() == Material.REDSTONE_COMPARATOR_ON | e.getClickedBlock().getType() == Material.DIODE_BLOCK_OFF | e.getClickedBlock().getType() == Material.DIODE_BLOCK_ON | e.getClickedBlock().getType() == Material.DRAGON_EGG | e.getClickedBlock().getType() == Material.NOTE_BLOCK) {
                    if (AdvancedLobby.cfg.getBoolean("disable_player_interaction")) {
                        if (!AdvancedLobby.build.contains(p)) e.setCancelled(true);
                    }
                }
                if (e.getClickedBlock().getType() == Material.FLOWER_POT) {
                    if (!AdvancedLobby.build.contains(p)) e.setCancelled(true);
                }
            }

            if (e.getAction() == Action.RIGHT_CLICK_AIR | e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (item != null) {
                    /*
                     * COMPASS >
                     */
                    if (item.getType() == Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.teleporter.material")) && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(AdvancedLobby.getString("hotbar_items.teleporter.displayname"))) {
                        if (AdvancedLobby.cfg.getBoolean("hotbar_items.teleporter.enabled")) {
                            e.setCancelled(true);
                            AdvancedLobby.playSound(p, p.getLocation(), "teleporter.open_inventory");
                            Inventories.openCompassInventory(p);
                            return;
                        }
                    }
                    /*
                     * COMPASS <
                     */

                    /*
                     * COSMETICS >
                     */
                    if (item.getType() == Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.cosmetics.material")) && item.hasItemMeta() && item
                            .getItemMeta().getDisplayName().equals(AdvancedLobby.getString("hotbar_items.cosmetics.displayname"))) {
                        if (AdvancedLobby.cfg.getBoolean("hotbar_items.cosmetics.enabled")) {
                            e.setCancelled(true);
                            AdvancedLobby.playSound(p, p.getLocation(), "cosmetics.open_inventory");
                            Inventories.openCosmetics(p);
                            return;
                        }
                    }
                    /*
                     * COSMETICS <
                     */

                    /*
                     * GADGETS >
                     */
                    if (item.getType() == Material.FEATHER && item.hasItemMeta() && item
                            .getItemMeta().getDisplayName().equals(AdvancedLobby.getString("hotbar_items.gadget.equipped.displayname").replace("%gadget%", AdvancedLobby.getString("inventories.cosmetics_gadgets.rocket_jump_gadget.displayname")))) {
                        if (AdvancedLobby.cfg.getBoolean("hotbar_items.gadget.enabled")) {
                            e.setCancelled(true);
                            if (Cosmetics.gadgetReloading.contains(p)) {
                                AdvancedLobby.playSound(p, p.getLocation(), "gadgets.reload_gadget");
                                return;
                            }
                            AdvancedLobby.playSound(p, p.getLocation(), "gadgets.rocket_jump");
                            p.playEffect(p.getLocation(), Effect.EXPLOSION_LARGE, 1);
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                if (p != players) {
                                    if (!AdvancedLobby.silentlobby.contains(p) && !AdvancedLobby.silentlobby.contains(players) && !AdvancedLobby.playerHider.containsKey(players)) {
                                        AdvancedLobby.playSound(players, p.getLocation(), "gadgets.rocket_jump");
                                        players.playEffect(p.getLocation(), Effect.EXPLOSION_LARGE, 1);
                                    }
                                }
                            }
                            Cosmetics.reloadGadget(p);
                            p.setVelocity(new Vector(0.0D, 1.0D, 0.0D));
                            return;
                        }
                    }
                    /*
                     * GADGETS <
                     */

                    /*
                     * HIDER >
                     */
                    if (item.getType() == Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.player_hider.show_all.material"))
                            | item.getType() == Material
                            .getMaterial(AdvancedLobby.cfg.getString("hotbar_items.player_hider.show_vip.material"))
                            | item.getType() == Material
                            .getMaterial(AdvancedLobby.cfg.getString("hotbar_items.player_hider.show_none.material"))
                            && item.hasItemMeta() && item.getItemMeta().getDisplayName()
                            .equals(AdvancedLobby.getString("hotbar_items.player_hider.show_all.displayname"))
                            | item.getItemMeta().getDisplayName()
                            .equals(AdvancedLobby.getString("hotbar_items.player_hider.show_vip.displayname"))
                            | item.getItemMeta().getDisplayName()
                            .equals(AdvancedLobby.getString("hotbar_items.player_hider.show_none.displayname"))) {
                        if (AdvancedLobby.cfg.getBoolean("hotbar_items.player_hider.enabled")) {
                            e.setCancelled(true);
                            if (AdvancedLobby.silentlobby.contains(p)) {
                                p.sendMessage(Locale.SILENTLOBBY_FUNCTION_BLOCKED.getMessage(p));
                                return;
                            }
                            if (AdvancedLobby.playerHider.containsKey(p)) {
                                if (AdvancedLobby.playerHider.get(p) == HiderType.NONE) {
                                    AdvancedLobby.playerHider.remove(p);
                                    p.sendMessage(Locale.HIDER_SHOW_ALL.getMessage(p));
                                    AdvancedLobby.playSound(p, p.getLocation(), "player_hider");
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
                                    ItemStack hider_all = new ItemBuilder(
                                            Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.player_hider.show_all.material")), 1,
                                            (short) AdvancedLobby.cfg.getInt("hotbar_items.player_hider.show_all.subid"))
                                            .setDisplayName(AdvancedLobby.getString("hotbar_items.player_hider.show_all.displayname"))
                                            .setLore(AdvancedLobby.cfg.getStringList("hotbar_items.player_hider.show_all.lore"));
                                    p.setItemInHand(hider_all);
                                    for (Player players : Bukkit.getOnlinePlayers()) {
                                        if (p != players) {
                                            if (!AdvancedLobby.silentlobby.contains(players)) {
                                                p.showPlayer(players);
                                            }
                                        }
                                    }
                                    return;
                                }
                                if (AdvancedLobby.playerHider.get(p) == HiderType.VIP) {
                                    AdvancedLobby.playerHider.put(p, HiderType.NONE);
                                    p.sendMessage(Locale.HIDER_SHOW_NONE.getMessage(p));
                                    AdvancedLobby.playSound(p, p.getLocation(), "player_hider");
                                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
                                    ItemStack hider_none = new ItemBuilder(
                                            Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.player_hider.show_none.material")), 1,
                                            (short) AdvancedLobby.cfg.getInt("hotbar_items.player_hider.show_none.subid"))
                                            .setDisplayName(AdvancedLobby.getString("hotbar_items.player_hider.show_none.displayname"))
                                            .setLore(AdvancedLobby.cfg.getStringList("hotbar_items.player_hider.show_none.lore"));
                                    p.setItemInHand(hider_none);
                                    for (Player players : Bukkit.getOnlinePlayers()) {
                                        p.hidePlayer(players);
                                    }
                                    return;
                                }
                                return;
                            }
                            AdvancedLobby.playerHider.put(p, HiderType.VIP);
                            p.sendMessage(Locale.HIDER_SHOW_VIP.getMessage(p));
                            AdvancedLobby.playSound(p, p.getLocation(), "player_hider");
                            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
                            ItemStack hider_vip = new ItemBuilder(
                                    Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.player_hider.show_vip.material")), 1,
                                    (short) AdvancedLobby.cfg.getInt("hotbar_items.player_hider.show_vip.subid"))
                                    .setDisplayName(AdvancedLobby.getString("hotbar_items.player_hider.show_vip.displayname"))
                                    .setLore(AdvancedLobby.cfg.getStringList("hotbar_items.player_hider.show_vip.lore"));
                            p.setItemInHand(hider_vip);
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                if (!players.hasPermission("advancedlobby.player_hider.bypass")) {
                                    p.hidePlayer(players);
                                }
                            }
                            return;
                        }
                    }
                    /*
                     * HIDER <
                     */

                    /*
                     * SILENTLOBBY >
                     */
                    if (item.getType() == Material
                            .getMaterial(AdvancedLobby.cfg.getString("hotbar_items.silentlobby.activated.material"))
                            | item.getType() == Material
                            .getMaterial(AdvancedLobby.cfg.getString("hotbar_items.silentlobby.deactivated.material"))
                            && item.hasItemMeta() && item.getItemMeta().getDisplayName()
                            .equals(AdvancedLobby.getString("hotbar_items.silentlobby.activated.displayname"))
                            | item.getItemMeta().getDisplayName()
                            .equals(AdvancedLobby.getString("hotbar_items.silentlobby.deactivated.displayname"))) {
                        if (AdvancedLobby.cfg.getBoolean("hotbar_items.silentlobby.enabled")) {
                            e.setCancelled(true);
                            if (!AdvancedLobby.silentlobby.contains(p)) {
                                AdvancedLobby.silentlobby.add(p);
                                AdvancedLobby.playerHider.put(p, HiderType.NONE);
                                p.sendMessage(Locale.SILENTLOBBY_JOIN.getMessage(p));
                                AdvancedLobby.playSound(p, p.getLocation(), "silentlobby.enable_silentlobby");
                                p.playEffect(p.getLocation(), Effect.EXPLOSION_HUGE, 1);
                                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));

                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    if (players != p) {
                                        players.hidePlayer(p);
                                        p.hidePlayer(players);
                                    }
                                }
                                ItemStack silentlobby_active = new ItemBuilder(
                                        Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.silentlobby.activated.material")),
                                        1, (short) AdvancedLobby.cfg.getInt("hotbar_items.silentlobby.activated.subid"))
                                        .setDisplayName(AdvancedLobby.getString("hotbar_items.silentlobby.activated.displayname"))
                                        .setLore(AdvancedLobby.cfg.getStringList("hotbar_items.silentlobby.activated.lore"));
                                p.setItemInHand(silentlobby_active);

                                ItemStack hider_none = new ItemBuilder(
                                        Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.player_hider.show_none.material")), 1,
                                        (short) AdvancedLobby.cfg.getInt("hotbar_items.player_hider.show_none.subid"))
                                        .setDisplayName(AdvancedLobby.getString("hotbar_items.player_hider.show_none.displayname"))
                                        .setLore(AdvancedLobby.cfg.getStringList("hotbar_items.player_hider.show_none.lore"));
                                if (AdvancedLobby.cfg.getBoolean("hotbar_items.player_hider.enabled")) {
                                    p.getInventory().setItem(AdvancedLobby.cfg.getInt("hotbar_items.player_hider.slot"), hider_none);
                                }

                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    p.hidePlayer(players);
                                    players.hidePlayer(p);
                                }
                                if (Cosmetics.balloons.containsKey(p)) {
                                    Bukkit.getScheduler().scheduleSyncDelayedTask(AdvancedLobby.getInstance(), () -> Cosmetics.balloons.get(p).remove(), 5L);
                                }
                                return;
                            }
                            AdvancedLobby.silentlobby.remove(p);
                            AdvancedLobby.playerHider.remove(p);
                            p.sendMessage(Locale.SILENTLOBBY_LEAVE.getMessage(p));
                            AdvancedLobby.playSound(p, p.getLocation(), "silentlobby.disable_silentlobby");
                            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
                            ItemStack silentlobby_deactive = new ItemBuilder(
                                    Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.silentlobby.deactivated.material")), 1,
                                    (short) AdvancedLobby.cfg.getInt("hotbar_items.silentlobby.deactivated.subid"))
                                    .setDisplayName(AdvancedLobby.getString("hotbar_items.silentlobby.deactivated.displayname"))
                                    .setLore(AdvancedLobby.cfg.getStringList("hotbar_items.silentlobby.deactivated.lore"));
                            p.setItemInHand(silentlobby_deactive);

                            ItemStack hider_all = new ItemBuilder(
                                    Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.player_hider.show_all.material")), 1,
                                    (short) AdvancedLobby.cfg.getInt("hotbar_items.player_hider.show_all.subid"))
                                    .setDisplayName(AdvancedLobby.getString("hotbar_items.player_hider.show_all.displayname"))
                                    .setLore(AdvancedLobby.cfg.getStringList("hotbar_items.player_hider.show_all.lore"));
                            if (AdvancedLobby.cfg.getBoolean("hotbar_items.player_hider.enabled")) {
                                p.getInventory().setItem(AdvancedLobby.cfg.getInt("hotbar_items.player_hider.slot"), hider_all);
                            }

                            for (Player players : Bukkit.getOnlinePlayers()) {
                                if (p != players) {
                                    if (!AdvancedLobby.silentlobby.contains(players)) {
                                        if (AdvancedLobby.playerHider.containsKey(players)) {
                                            if (AdvancedLobby.playerHider.get(players) == HiderType.VIP && p.hasPermission("advancedlobby.player_hider.bypass")) {
                                                players.showPlayer(p);
                                            }
                                            if (AdvancedLobby.playerHider.get(players) == HiderType.NONE) {
                                                players.hidePlayer(p);
                                            }
                                            p.showPlayer(players);
                                            return;
                                        } else {
                                            p.showPlayer(players);
                                            players.showPlayer(p);
                                        }
                                    }
                                }
                            }
                            return;
                        }
                    }
                    /*
                     * SILENTLOBBY <
                     */

                    /*
                     * SHIELD >
                     */
                    if (item.getType() == Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.shield.activated.material"))
                            | item.getType() == Material
                            .getMaterial(AdvancedLobby.cfg.getString("hotbar_items.shield.deactivated.material"))
                            && item.hasItemMeta() && item.getItemMeta().getDisplayName()
                            .equals(AdvancedLobby.getString("hotbar_items.shield.activated.displayname"))
                            | item.getItemMeta().getDisplayName()
                            .equals(AdvancedLobby.getString("hotbar_items.shield.deactivated.displayname"))) {
                        if (AdvancedLobby.cfg.getBoolean("hotbar_items.shield.enabled")) {
                            e.setCancelled(true);
                            if (!AdvancedLobby.shield.contains(p)) {
                                AdvancedLobby.shield.add(p);
                                p.sendMessage(Locale.SHIELD_ACTIVATE.getMessage(p));
                                AdvancedLobby.playSound(p, p.getLocation(), "shield.enable_shield");
                                ItemStack shield_active = new ItemBuilder(
                                        Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.shield.activated.material")), 1,
                                        (short) AdvancedLobby.cfg.getInt("hotbar_items.shield.activated.subid"))
                                        .setDisplayName(AdvancedLobby.getString("hotbar_items.shield.activated.displayname"))
                                        .setLore(AdvancedLobby.cfg.getStringList("hotbar_items.shield.activated.lore"));
                                p.setItemInHand(shield_active);

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

                                return;
                            }
                            AdvancedLobby.shield.remove(p);
                            p.sendMessage(Locale.SHIELD_DEACTIVATE.getMessage(p));
                            AdvancedLobby.playSound(p, p.getLocation(), "shield.disable_shield");
                            ItemStack shield_deactive = new ItemBuilder(
                                    Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.shield.deactivated.material")), 1,
                                    (short) AdvancedLobby.cfg.getInt("hotbar_items.shield.deactivated.subid"))
                                    .setDisplayName(AdvancedLobby.getString("hotbar_items.shield.deactivated.displayname"))
                                    .setLore(AdvancedLobby.cfg.getStringList("hotbar_items.shield.deactivated.lore"));
                            p.setItemInHand(shield_deactive);
                            return;
                        }
                    }
                    /*
                     * SHIELD <
                     */

                    /*
                     * CUSTOM ITEM >
                     */
                    if (item.getType() == Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.custom_item.material")) && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(AdvancedLobby.getString("hotbar_items.custom_item.displayname"))) {
                        if (AdvancedLobby.cfg.getBoolean("hotbar_items.custom_item.enabled")) {
                            e.setCancelled(true);
                            if (AdvancedLobby.cfg.getString("hotbar_items.custom_item.command") != null) {
                                p.performCommand(AdvancedLobby.cfg.getString("hotbar_items.custom_item.command"));
                            }
                            return;
                        }
                    }
                    /*
                     * CUSTOM ITEM <
                     */
                }
            }

        }

    }

}
