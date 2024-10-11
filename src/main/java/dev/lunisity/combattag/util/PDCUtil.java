package dev.lunisity.combattag.util;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class PDCUtil {

    public static Entity setString(final JavaPlugin clazz, final Entity entity, final String key, final String value) {
        if (entity == null) return null;
        PersistentDataContainer pdc = entity.getPersistentDataContainer();

        NamespacedKey namespacedKey = new NamespacedKey(clazz, key);
        pdc.set(namespacedKey, PersistentDataType.STRING, value);

        return entity;
    }

    public static String getString(final JavaPlugin clazz, final Entity entity, final String key) {
        if (entity == null) return null;
        PersistentDataContainer pdc = entity.getPersistentDataContainer();

        NamespacedKey namespacedKey = new NamespacedKey(clazz, key);

        if (!pdc.has(namespacedKey, PersistentDataType.STRING)) return null;

        return pdc.get(namespacedKey, PersistentDataType.STRING);
    }

    public static ItemStack setDouble(final JavaPlugin clazz, final ItemStack itemStack, final String key, final double value) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return null;

        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(clazz, key);

        pdc.set(namespacedKey, PersistentDataType.DOUBLE, value);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack setLong(final JavaPlugin clazz, final ItemStack itemStack, final String key, final long value) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return null;

        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(clazz, key);

        pdc.set(namespacedKey, PersistentDataType.LONG, value);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack setString(final JavaPlugin clazz, final ItemStack itemStack, final String key, final String value) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return null;

        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(clazz, key);

        pdc.set(namespacedKey, PersistentDataType.STRING, value);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static Double getDouble(final JavaPlugin clazz, final ItemStack itemStack, final String key) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return null;

        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(clazz, key);

        if (!pdc.has(namespacedKey, PersistentDataType.DOUBLE)) return null;

        return pdc.get(namespacedKey, PersistentDataType.DOUBLE);
    }

    public static String getString(final JavaPlugin clazz, final ItemStack itemStack, final String key) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return null;

        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(clazz, key);

        if (!pdc.has(namespacedKey, PersistentDataType.STRING)) return null;

        return pdc.get(namespacedKey, PersistentDataType.STRING);
    }

}
