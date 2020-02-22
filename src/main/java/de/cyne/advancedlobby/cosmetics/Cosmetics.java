package de.cyne.advancedlobby.cosmetics;

import de.cyne.advancedlobby.AdvancedLobby;
import de.cyne.advancedlobby.itembuilder.ItemBuilder;
import de.cyne.advancedlobby.locale.Locale;
import de.cyne.advancedlobby.misc.Balloon;
import org.bukkit.*;
import org.bukkit.entity.Bat;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class Cosmetics {

    public static HashMap<Player, HatType> hats = new HashMap<>();
    public static HashMap<Player, ParticleType> particles = new HashMap<>();
    public static HashMap<Player, Balloon> balloons = new HashMap<>();
    public static HashMap<Player, GadgetType> gadgets = new HashMap<>();
    public static ArrayList<Player> gadgetReloading = new ArrayList<>();

    public enum HatType {
        MELON_BLOCK, TNT, GLASS, SPONGE, PUMPKIN, CACTUS
    }

    public enum ParticleType {
        HEART, MUSIC, FLAMES, VILLAGER, RAINBOW
    }

    public enum BalloonType {
        YELLOW, RED, GREEN, BLUE, HAY_BLOCK, SEA_LANTERN, BOOKSHELF, NOTE_BLOCK
    }

    public enum GadgetType {
        GRAPPLING_HOOK, ROCKET_JUMP
    }

    public static void equipHat(Player player, HatType type) {
        if (hats.containsKey(player)) {
            hats.remove(player);
        }
        ItemStack hat = null;
        String message = null;
        switch (type) {
            case MELON_BLOCK:
                if (player.hasPermission("advancedlobby.cosmetics.hats.melon")) {
                    hat = new ItemBuilder(Material.MELON_BLOCK).setDisplayName(AdvancedLobby.getString("inventories.cosmetics_hats.melon_hat.displayname"));
                    hats.put(player, HatType.MELON_BLOCK);
                    message = Locale.COSMETICS_HATS_EQUIP.getMessage(player).replace("%hat%", AdvancedLobby.getString("inventories.cosmetics_hats.melon_hat.displayname"));
                    break;
                }
                message = Locale.COSMETICS_HATS_NO_PERMISSION.getMessage(player).replace("%hat%", AdvancedLobby.getString("inventories.cosmetics_hats.melon_hat.displayname"));
                break;
            case TNT:
                if (player.hasPermission("advancedlobby.cosmetics.hats.tnt")) {
                    hat = new ItemBuilder(Material.TNT).setDisplayName(AdvancedLobby.getString("inventories.cosmetics_hats.tnt_hat.displayname"));
                    hats.put(player, HatType.TNT);
                    message = Locale.COSMETICS_HATS_EQUIP.getMessage(player).replace("%hat%", AdvancedLobby.getString("inventories.cosmetics_hats.tnt_hat.displayname"));
                    break;
                }
                message = Locale.COSMETICS_HATS_NO_PERMISSION.getMessage(player).replace("%hat%", AdvancedLobby.getString("inventories.cosmetics_hats.tnt_hat.displayname"));
                break;
            case GLASS:
                if (player.hasPermission("advancedlobby.cosmetics.hats.glass")) {
                    hat = new ItemBuilder(Material.GLASS).setDisplayName(AdvancedLobby.getString("inventories.cosmetics_hats.glass_hat.displayname"));
                    hats.put(player, HatType.GLASS);
                    message = Locale.COSMETICS_HATS_EQUIP.getMessage(player).replace("%hat%", AdvancedLobby.getString("inventories.cosmetics_hats.glass_hat.displayname"));
                    break;
                }
                message = Locale.COSMETICS_HATS_NO_PERMISSION.getMessage(player).replace("%hat%", AdvancedLobby.getString("inventories.cosmetics_hats.glass_hat.displayname"));
                break;
            case SPONGE:
                if (player.hasPermission("advancedlobby.cosmetics.hats.sponge")) {
                    hat = new ItemBuilder(Material.SPONGE).setDisplayName(AdvancedLobby.getString("inventories.cosmetics_hats.sponge_hat.displayname"));
                    hats.put(player, HatType.SPONGE);
                    message = Locale.COSMETICS_HATS_EQUIP.getMessage(player).replace("%hat%", AdvancedLobby.getString("inventories.cosmetics_hats.sponge_hat.displayname"));
                    break;
                }
                message = Locale.COSMETICS_HATS_NO_PERMISSION.getMessage(player).replace("%hat%", AdvancedLobby.getString("inventories.cosmetics_hats.sponge_hat.displayname"));
                break;
            case PUMPKIN:
                if (player.hasPermission("advancedlobby.cosmetics.hats.pumpkin")) {
                    hat = new ItemBuilder(Material.PUMPKIN).setDisplayName(AdvancedLobby.getString("inventories.cosmetics_hats.pumpkin_hat.displayname"));
                    hats.put(player, HatType.PUMPKIN);
                    message = Locale.COSMETICS_HATS_EQUIP.getMessage(player).replace("%hat%", AdvancedLobby.getString("inventories.cosmetics_hats.pumpkin_hat.displayname"));
                    break;
                }
                message = Locale.COSMETICS_HATS_NO_PERMISSION.getMessage(player).replace("%hat%", AdvancedLobby.getString("inventories.cosmetics_hats.pumpkin_hat.displayname"));
                break;
            case CACTUS:
                if (player.hasPermission("advancedlobby.cosmetics.hats.cactus")) {
                    hat = new ItemBuilder(Material.CACTUS).setDisplayName(AdvancedLobby.getString("inventories.cosmetics_hats.cactus_hat.displayname"));
                    hats.put(player, HatType.CACTUS);
                    message = Locale.COSMETICS_HATS_EQUIP.getMessage(player).replace("%hat%", AdvancedLobby.getString("inventories.cosmetics_hats.cactus_hat.displayname"));
                    break;
                }
                message = Locale.COSMETICS_HATS_NO_PERMISSION.getMessage(player).replace("%hat%", AdvancedLobby.getString("inventories.cosmetics_hats.cactus_hat.displayname"));
                break;
        }
        player.getInventory().setHelmet(hat);
        player.sendMessage(message);
    }

    public static void equipBalloon(Player player, BalloonType type) {
        Balloon balloon = null;
        switch (type) {
            case YELLOW:
                balloon = new Balloon(player, Material.STAINED_CLAY, (byte) 4);
                break;
            case RED:
                balloon = new Balloon(player, Material.STAINED_CLAY, (byte) 14);
                break;
            case GREEN:
                balloon = new Balloon(player, Material.STAINED_CLAY, (byte) 5);
                break;
            case BLUE:
                balloon = new Balloon(player, Material.STAINED_CLAY, (byte) 3);
                break;
            case HAY_BLOCK:
                balloon = new Balloon(player, Material.HAY_BLOCK, (byte) 0);
                break;
            case SEA_LANTERN:
                balloon = new Balloon(player, Material.SEA_LANTERN, (byte) 0);
                break;
            case BOOKSHELF:
                balloon = new Balloon(player, Material.BOOKSHELF, (byte) 0);
                break;
            case NOTE_BLOCK:
                balloon = new Balloon(player, Material.NOTE_BLOCK, (byte) 0);
                break;
        }
        if (type != null) {
            if(!AdvancedLobby.silentlobby.contains(player)) {
                balloon.create();
            }
            balloons.put(player, balloon);


        }
    }

    public static void equipGadget(Player player, GadgetType type) {
        ItemStack gadget = null;
        switch (type) {
            case GRAPPLING_HOOK:
                gadget = new ItemBuilder(Material.FISHING_ROD).setDisplayName(AdvancedLobby.getString("hotbar_items.gadget.equipped.displayname").replace("%gadget%", AdvancedLobby.getString("inventories.cosmetics_gadgets.grappling_hook_gadget.displayname"))).setLore(AdvancedLobby.cfg.getStringList("hotbar_items.gadget.equipped.lore")).setUnbreakable(true).addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                break;
            case ROCKET_JUMP:
                gadget = new ItemBuilder(Material.FEATHER).setDisplayName(AdvancedLobby.getString("hotbar_items.gadget.equipped.displayname").replace("%gadget%", AdvancedLobby.getString("inventories.cosmetics_gadgets.rocket_jump_gadget.displayname"))).setLore(AdvancedLobby.cfg.getStringList("hotbar_items.gadget.equipped.lore"));
                break;
        }
        player.getInventory().setItem(AdvancedLobby.cfg.getInt("hotbar_items.gadget.slot"), gadget);
        gadgets.put(player, type);
    }

    public static void startBalloonTask() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(AdvancedLobby.getInstance(), () -> {
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (balloons.containsKey(players)) {
                    if (AdvancedLobby.bungeecord | players.getWorld() == AdvancedLobby.lobbyWorld) {
                        if (!AdvancedLobby.silentlobby.contains(players)) {
                            if(balloons.get(players).getFallingBlock() == null) {
                                balloons.get(players).create();
                            }
                            if (balloons.get(players).getFallingBlock().isDead() | balloons.get(players).getBat().isDead()) {
                                balloons.get(players).remove();
                                balloons.get(players).create();
                            }
                            Bat localBat = balloons.get(players).getBat();

                            Location location = players.getLocation();
                            location.setYaw(location.getYaw() + 90.0F);
                            location.setPitch(-45.0F);

                            Vector direction = location.getDirection().normalize();
                            location.add(direction.getX() * 1.5D, direction.getY() * 1.5D + 0.5D, direction.getZ() * 1.5D);

                            Vector locationVector = location.toVector();
                            Vector batVector = balloons.get(players).getBat().getLocation().toVector();

                            localBat.setVelocity(locationVector.clone().subtract(batVector).multiply(0.5D));
                        }
                    }
                }
            }
        }, 0L, 3L);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(AdvancedLobby.getInstance(), () -> {
            for (Player players : Bukkit.getOnlinePlayers()) {
                for (FallingBlock fallingBlocks : Balloon.fallingBlocks.values()) {
                    if (AdvancedLobby.bungeecord | players.getWorld() == AdvancedLobby.lobbyWorld) {
                        if (!AdvancedLobby.silentlobby.contains(players)) {
                            players.spigot().playEffect(fallingBlocks.getLocation(), Effect.SPELL, 1, 1, 0.0f, 0.0f, 0.0f, 0.1f, 8,
                                    4);
                        }
                    }
                }
            }
        }, 90L, 90L);
    }

    public static void reloadGadget(Player player) {
        Cosmetics.gadgetReloading.add(player);
        Bukkit.getScheduler().scheduleSyncDelayedTask(AdvancedLobby.getInstance(), () -> {
            if (player.isOnline()) {
                Cosmetics.gadgetReloading.remove(player);
            }
        }, 100L);
    }

}
