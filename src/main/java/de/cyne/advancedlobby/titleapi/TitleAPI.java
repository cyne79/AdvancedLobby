package de.cyne.advancedlobby.titleapi;

import com.sun.org.apache.xerces.internal.impl.dv.xs.AnySimpleDV;
import de.cyne.advancedlobby.AdvancedLobby;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class TitleAPI {

    public static void sendActionBar(Player player, String message) {
        if (AdvancedLobby.titleApi_oldVersion) {
            try {
                Object action = TitleAPI.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                        .getMethod("a", String.class).invoke(null, "{\"text\":\"" + message + "\"}");
                Constructor<?> actionConstructor = TitleAPI.getNMSClass("PacketPlayOutChat")
                        .getConstructor(new Class[]{TitleAPI.getNMSClass("IChatBaseComponent"), Byte.TYPE});
                Object packet = actionConstructor.newInstance(new Object[]{action, (byte) 2});
                sendPacket(player, packet);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                Object packet;
                Class<?> packetPlayOutChatClass = TitleAPI.getNMSClass("PacketPlayOutChat");

                Class<?> chatComponentTextClass = TitleAPI.getNMSClass("ChatComponentText");
                Class<?> iChatBaseComponentClass = TitleAPI.getNMSClass("IChatBaseComponent");
                try {
                    Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + AdvancedLobby.version + ".ChatMessageType");
                    Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
                    Object chatMessageType = null;
                    for (Object obj : chatMessageTypes) {
                        if (obj.toString().equals("GAME_INFO")) {
                            chatMessageType = obj;
                        }
                    }
                    Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                    packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, chatMessageTypeClass}).newInstance(chatCompontentText, chatMessageType);
                } catch (ClassNotFoundException cnfe) {
                    Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                    packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(chatCompontentText, (byte) 2);
                }
                sendPacket(player, packet);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public static void sendTabTitle(Player player, String header, String footer) {
        if (header == null)
            header = "";
        header = ChatColor.translateAlternateColorCodes('&', header);

        if (footer == null)
            footer = "";
        footer = ChatColor.translateAlternateColorCodes('&', footer);
        try {
            Object tabHeader = TitleAPI.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                    .getMethod("a", String.class).invoke(null, "{\"text\":\"" + header + "\"}");
            Object tabFooter = TitleAPI.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                    .getMethod("a", String.class).invoke(null, "{\"text\":\"" + footer + "\"}");
            Constructor<?> titleConstructor = TitleAPI.getNMSClass("PacketPlayOutPlayerListHeaderFooter")
                    .getConstructor();
            Object packet = titleConstructor.newInstance();
            try {
                Field aField = packet.getClass().getDeclaredField("a");
                aField.setAccessible(true);
                aField.set(packet, tabHeader);
                Field bField = packet.getClass().getDeclaredField("b");
                bField.setAccessible(true);
                bField.set(packet, tabFooter);
            } catch (Exception ex) {
                Field aField = packet.getClass().getDeclaredField("header");
                aField.setAccessible(true);
                aField.set(packet, tabHeader);
                Field bField = packet.getClass().getDeclaredField("footer");
                bField.setAccessible(true);
                bField.set(packet, tabFooter);
            }
            sendPacket(player, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title,
                                 String subtitle) {
        try {
            if (title != null) {
                Object e = TitleAPI.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES")
                        .get((Object) null);
                Object chatTitle = TitleAPI.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                        .getMethod("a", new Class[]{String.class})
                        .invoke((Object) null, new Object[]{"{\"text\":\"" + title + "\"}"});
                Constructor<?> subtitleConstructor = TitleAPI.getNMSClass("PacketPlayOutTitle")
                        .getConstructor(new Class[]{
                                TitleAPI.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
                                TitleAPI.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
                Object titlePacket = subtitleConstructor
                        .newInstance(new Object[]{e, chatTitle, fadeIn, stay, fadeOut});
                sendPacket(player, titlePacket);

                e = TitleAPI.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE")
                        .get((Object) null);
                chatTitle = TitleAPI.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                        .getMethod("a", new Class[]{String.class})
                        .invoke((Object) null, new Object[]{"{\"text\":\"" + title + "\"}"});
                subtitleConstructor = TitleAPI.getNMSClass("PacketPlayOutTitle").getConstructor(
                        new Class[]{TitleAPI.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
                                TitleAPI.getNMSClass("IChatBaseComponent")});
                titlePacket = subtitleConstructor.newInstance(new Object[]{e, chatTitle});
                sendPacket(player, titlePacket);
            }
            if (subtitle != null) {
                Object e = TitleAPI.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES")
                        .get((Object) null);
                Object chatSubtitle = TitleAPI.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                        .getMethod("a", new Class[]{String.class})
                        .invoke((Object) null, new Object[]{"{\"text\":\"" + title + "\"}"});
                Constructor<?> subtitleConstructor = TitleAPI.getNMSClass("PacketPlayOutTitle")
                        .getConstructor(new Class[]{
                                TitleAPI.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
                                TitleAPI.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
                Object subtitlePacket = subtitleConstructor
                        .newInstance(new Object[]{e, chatSubtitle, fadeIn, stay, fadeOut});
                sendPacket(player, subtitlePacket);

                e = TitleAPI.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE")
                        .get((Object) null);
                chatSubtitle = TitleAPI.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
                        .getMethod("a", new Class[]{String.class})
                        .invoke((Object) null, new Object[]{"{\"text\":\"" + subtitle + "\"}"});
                subtitleConstructor = TitleAPI.getNMSClass("PacketPlayOutTitle")
                        .getConstructor(new Class[]{
                                TitleAPI.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
                                TitleAPI.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
                subtitlePacket = subtitleConstructor
                        .newInstance(new Object[]{e, chatSubtitle, fadeIn, stay, fadeOut});
                sendPacket(player, subtitlePacket);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", new Class[]{TitleAPI.getNMSClass("Packet")})
                    .invoke(playerConnection, new Object[]{packet});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
