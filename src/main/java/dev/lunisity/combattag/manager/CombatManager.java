package dev.lunisity.combattag.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.lunisity.combattag.CombatTag;
import dev.lunisity.combattag.listener.CombatListener;
import dev.lunisity.combattag.util.ColorAPI;
import dev.lunisity.combattag.util.PDCUtil;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Getter
public class CombatManager {

    private final int length = CombatTag.get().getConfig().getInt("Seconds");
    public final Cache<UUID, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(length, TimeUnit.SECONDS).build();
    public final Map<UUID, ItemStack[]> contents = new HashMap<>();
    public final Map<UUID, Entity> logged = new HashMap<>();

    private final CombatTag ct;

    public CombatManager(final CombatTag ct) {
        this.ct = ct;
    }

    public Entity spawnLogger(final Player player) {
        if (cooldown.asMap().containsKey(player.getUniqueId())) {
            if (player.getLocation().getWorld() == null) return null;
            Entity entity = player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);

            Villager villager = (Villager) entity;
            villager.setAI(false);
            villager.setCanPickupItems(false);
            villager.setCustomNameVisible(true);

            String name = Objects.requireNonNull(this.ct.getConfig().getString("Logger-Format")).replace("%player%", player.getName());
            entity.setCustomName(ColorAPI.apply(name));
            PDCUtil.setString(JavaPlugin.getPlugin(CombatTag.class), entity, "Owner", player.getUniqueId().toString());

            contents.put(player.getUniqueId(), player.getInventory().getContents());
            logged.put(player.getUniqueId(), entity);

            return entity;
        }
        return null;
    }

    public void tag(final Entity damager, final Entity target) {
        if (!(damager instanceof Player attacker)) return;
        if (!(target instanceof Player victim)) return;
        if (!attacker.getWorld().getPVP()) return;

        cooldown.put(attacker.getUniqueId(), System.currentTimeMillis() + (length * 1000L));
        contents.put(attacker.getUniqueId(), attacker.getInventory().getContents());

        cooldown.put(target.getUniqueId(), System.currentTimeMillis() + (length * 1000L));
        contents.put(target.getUniqueId(), victim.getInventory().getContents());

        int time = this.ct.getConfig().getInt("Seconds");
        String message = ColorAPI.apply(Objects.requireNonNull(this.ct.getConfig().getString("Messages.Tagged")).replace("%time%", time + "s"));

        attacker.sendMessage(message);
        victim.sendMessage(message);
    }

}
