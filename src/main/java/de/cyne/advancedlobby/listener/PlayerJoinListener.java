package de.cyne.advancedlobby.listener;

import de.cyne.advancedlobby.AdvancedLobby;
import de.cyne.advancedlobby.itembuilder.ItemBuilder;
import de.cyne.advancedlobby.locale.Locale;
import de.cyne.advancedlobby.misc.HiderType;
import de.cyne.advancedlobby.misc.LocationManager;
import de.cyne.advancedlobby.titleapi.TitleAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        e.setJoinMessage(Locale.JOIN_MESSAGE.getMessage(p).replace("%player%", AdvancedLobby.getName(p)));

        double health = AdvancedLobby.cfg.getDouble("player_join.health");
        p.setMaxHealth(health);
        p.setHealth(health);

        p.setFoodLevel(20);

        p.setAllowFlight(false);
        p.setFlying(false);

        p.setFireTicks(0);

        p.setLevel(0);
        p.setExp(0);

        for (PotionEffect effects : p.getActivePotionEffects()) {
            p.removePotionEffect(effects.getType());
        }

        GameMode gameMode;
        String mode = AdvancedLobby.cfg.getString("player_join.gamemode");

        switch (mode) {
            case ("0"):
                gameMode = GameMode.SURVIVAL;
                break;
            case ("1"):
                gameMode = GameMode.CREATIVE;
                break;
            case ("2"):
                gameMode = GameMode.ADVENTURE;
                break;
            case ("3"):
                gameMode = GameMode.SPECTATOR;
                break;
            default:
                gameMode = GameMode.SURVIVAL;
                break;
        }

        p.setGameMode(gameMode);

        if (AdvancedLobby.cfg.getBoolean("player_join.clear_inventory")) {
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            p.updateInventory();
        }

        if (AdvancedLobby.cfg.getBoolean("title.enabled")) {
            if (AdvancedLobby.placeholderApi) {
                TitleAPI.sendTitle(p, 20, 40, 20, AdvancedLobby.getPlaceholderString(p, "title.title").replace("%player%", AdvancedLobby.getName(p)), AdvancedLobby.getPlaceholderString(p, "title.subtitle").replace("%player%", AdvancedLobby.getName(p)));
            } else {
                TitleAPI.sendTitle(p, 20, 40, 20, AdvancedLobby.getString("title.title").replace("%player%", AdvancedLobby.getName(p)), AdvancedLobby.getString("title.subtitle").replace("%player%", AdvancedLobby.getName(p)));
            }
        }


        if (AdvancedLobby.cfg.getBoolean("tablist_header_footer.enabled")) {
            if (AdvancedLobby.cfg.getBoolean("tablist_header_footer.update_tablist")) {
                Bukkit.getScheduler().scheduleSyncRepeatingTask(AdvancedLobby.getInstance(), () -> {
                    if (AdvancedLobby.placeholderApi) {
                        TitleAPI.sendTabTitle(p, AdvancedLobby.getPlaceholderString(p, "tablist_header_footer.header"),
                                AdvancedLobby.getPlaceholderString(p, "tablist_header_footer.footer"));
                    } else {
                        TitleAPI.sendTabTitle(p, AdvancedLobby.getString("tablist_header_footer.header"),
                                AdvancedLobby.getString("tablist_header_footer.footer"));
                    }
                }, 0L, 20L);
            } else {
                if (AdvancedLobby.placeholderApi) {
                    TitleAPI.sendTabTitle(p, AdvancedLobby.getPlaceholderString(p, "tablist_header_footer.header"),
                            AdvancedLobby.getPlaceholderString(p, "tablist_header_footer.footer"));
                } else {
                    TitleAPI.sendTabTitle(p, AdvancedLobby.getString("tablist_header_footer.header"),
                            AdvancedLobby.getString("tablist_header_footer.footer"));
                }
            }


        }


        ItemBuilder teleporter = new ItemBuilder(Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.teleporter.material")), 1,
                (short) AdvancedLobby.cfg.getInt("hotbar_items.teleporter.subid")).setDisplayName(
                ChatColor.translateAlternateColorCodes('&', AdvancedLobby.cfg.getString("hotbar_items.teleporter.displayname")))
                .setLore(AdvancedLobby.cfg.getStringList("hotbar_items.teleporter.lore"));

        if (AdvancedLobby.cfg.getBoolean("hotbar_items.teleporter.enabled")) {
            p.getInventory().setItem(AdvancedLobby.cfg.getInt("hotbar_items.teleporter.slot"), teleporter);
        }

        ItemBuilder hider = new ItemBuilder(Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.player_hider.show_all.material")),
                1, (short) AdvancedLobby.cfg.getInt("hotbar_items.player_hider.show_all.subid"))
                .setDisplayName(ChatColor.translateAlternateColorCodes('&',
                        AdvancedLobby.cfg.getString("hotbar_items.player_hider.show_all.displayname")))
                .setLore(AdvancedLobby.cfg.getStringList("hotbar_items.player_hider.show_all.lore"));

        if (AdvancedLobby.cfg.getBoolean("hotbar_items.player_hider.enabled")) {
            p.getInventory().setItem(AdvancedLobby.cfg.getInt("hotbar_items.player_hider.slot"), hider);
        }

        ItemBuilder cosmetics = new ItemBuilder(Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.cosmetics.material")), 1,
                (short) AdvancedLobby.cfg.getInt("hotbar_items.cosmetics.subid")).setDisplayName(
                ChatColor.translateAlternateColorCodes('&', AdvancedLobby.cfg.getString("hotbar_items.cosmetics.displayname")))
                .setLore(AdvancedLobby.cfg.getStringList("hotbar_items.cosmetics.lore"));

        if (AdvancedLobby.cfg.getBoolean("hotbar_items.cosmetics.enabled")) {
            p.getInventory().setItem(AdvancedLobby.cfg.getInt("hotbar_items.cosmetics.slot"), cosmetics);
        }

        ItemBuilder no_gadget = new ItemBuilder(Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.gadget.unequipped.material")), 1,
                (short) AdvancedLobby.cfg.getInt("hotbar_items.gadget.unequipped.subid")).setDisplayName(
                ChatColor.translateAlternateColorCodes('&', AdvancedLobby.cfg.getString("hotbar_items.gadget.unequipped.displayname")))
                .setLore(AdvancedLobby.cfg.getStringList("hotbar_items.gadget.unequipped.lore"));

        if (AdvancedLobby.cfg.getBoolean("hotbar_items.gadget.enabled")) {
            p.getInventory().setItem(AdvancedLobby.cfg.getInt("hotbar_items.gadget.slot"), no_gadget);
        }

        ItemBuilder silentlobby = new ItemBuilder(
                Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.silentlobby.deactivated.material")), 1,
                (short) AdvancedLobby.cfg.getInt("hotbar_items.silentlobby.deactivated.subid"))
                .setDisplayName(ChatColor.translateAlternateColorCodes('&',
                        AdvancedLobby.cfg.getString("hotbar_items.silentlobby.deactivated.displayname")))
                .setLore(AdvancedLobby.cfg.getStringList("hotbar_items.silentlobby.deactivated.lore"));

        if (AdvancedLobby.cfg.getBoolean("hotbar_items.silentlobby.enabled") && p.hasPermission("advancedlobby.silentlobby")) {
            p.getInventory().setItem(AdvancedLobby.cfg.getInt("hotbar_items.silentlobby.slot"), silentlobby);
        }

        ItemBuilder shield = new ItemBuilder(
                Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.shield.deactivated.material")), 1,
                (short) AdvancedLobby.cfg.getInt("hotbar_items.shield.deactivated.subid"))
                .setDisplayName(ChatColor.translateAlternateColorCodes('&',
                        AdvancedLobby.cfg.getString("hotbar_items.shield.deactivated.displayname")))
                .setLore(AdvancedLobby.cfg.getStringList("hotbar_items.shield.deactivated.lore"));

        if (AdvancedLobby.cfg.getBoolean("hotbar_items.shield.enabled") && p.hasPermission("advancedlobby.shield")) {
            p.getInventory().setItem(AdvancedLobby.cfg.getInt("hotbar_items.shield.slot"), shield);
        }

        ItemBuilder custom_item = new ItemBuilder(
                Material.getMaterial(AdvancedLobby.cfg.getString("hotbar_items.custom_item.material")), 1,
                (short) AdvancedLobby.cfg.getInt("hotbar_items.custom_item.subid"))
                .setDisplayName(ChatColor.translateAlternateColorCodes('&',
                        AdvancedLobby.cfg.getString("hotbar_items.custom_item.displayname")))
                .setLore(AdvancedLobby.cfg.getStringList("hotbar_items.custom_item.lore"));

        if (AdvancedLobby.cfg.getBoolean("hotbar_items.custom_item.enabled") && p.hasPermission("advancedlobby.custom_item")) {
            p.getInventory().setItem(AdvancedLobby.cfg.getInt("hotbar_items.custom_item.slot"), custom_item);
        }

        for (Player players : AdvancedLobby.playerHider.keySet()) {
            if(AdvancedLobby.playerHider.get(players) == HiderType.VIP) {
                if(!p.hasPermission("advancedlobby.player_hider.bypass")) {
                    players.hidePlayer(p);
                }
            }
            if(AdvancedLobby.playerHider.get(players) == HiderType.NONE) {
                players.hidePlayer(p);
            }
        }

        for (Player players : AdvancedLobby.silentlobby) {
            players.hidePlayer(p);
            p.hidePlayer(players);
        }

        if (!p.hasPlayedBefore()) {
            Location location = LocationManager.getLocation(AdvancedLobby.cfg.getString("spawn_location"));
            if (location != null) {
                p.teleport(location);
            }
        }

        if (AdvancedLobby.cfg.getBoolean("player_join.join_at_spawn")) {
            Location location = LocationManager.getLocation(AdvancedLobby.cfg.getString("spawn_location"));
            if (location != null) {
                p.teleport(location);
            }
        }

        if (AdvancedLobby.updateAvailable && p.hasPermission("advancedlobby.admin")) {
            TextComponent message = new TextComponent("§8┃ §bAdvancedLobby §8┃ §7Download now §8▶ ");
            TextComponent extra = new TextComponent("§8*§aclick§8*");

            extra.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8» §7Redirect to §bhttps://spigotmc.org/").create()));
            extra.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://spigotmc.org/resources/35799/"));

            message.addExtra(extra);


            p.sendMessage("");
            p.sendMessage("§8┃ §bAdvancedLobby §8┃ §7A §anew update §7for §bAdvancedLobby §7was found§8.");
            p.spigot().sendMessage(message);
            p.sendMessage("");
        }

        if (AdvancedLobby.devMode && p.hasPermission("advancedlobby.admin")) {
            p.sendMessage("");
            p.sendMessage("§8┃ §bAdvancedLobby §8┃ §cAdvanced development mode is active.");
            p.sendMessage("§8┃ §bAdvancedLobby §8┃ §cIf you are not the author of this plugin, contact him.");
            p.sendMessage("§8┃ §bAdvancedLobby §8┃ §8§m-----------------------------------");
            p.sendMessage("§8┃ §bAdvancedLobby §8┃ §7Current version§8: §f" + AdvancedLobby.updater.getCurrentVersion());
            p.sendMessage("§8┃ §bAdvancedLobby §8┃ §7Latest published version§8: §f" + AdvancedLobby.updater.getLatestVersion());
            p.sendMessage("");
        }

    }

}
