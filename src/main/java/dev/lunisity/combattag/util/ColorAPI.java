package dev.lunisity.combattag.util;

import org.bukkit.ChatColor;

public class ColorAPI {

    public static String apply(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
