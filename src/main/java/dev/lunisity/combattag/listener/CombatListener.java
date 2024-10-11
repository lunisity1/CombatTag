package dev.lunisity.combattag.listener;

import dev.lunisity.combattag.CombatTag;
import dev.lunisity.combattag.util.ColorAPI;
import dev.lunisity.combattag.util.EntityUtil;
import dev.lunisity.combattag.util.InvUtil;
import dev.lunisity.combattag.util.PDCUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public class CombatListener implements Listener {

    private final CombatTag ct;

    public CombatListener(final CombatTag ct) {
        this.ct = ct;
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!ct.getCombatManager().getCooldown().asMap().containsKey(event.getDamager().getUniqueId())) {
            this.ct.getCombatManager().tag(event.getDamager(), event.getEntity());
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!ct.getCombatManager().getCooldown().asMap().containsKey(event.getDamager().getUniqueId())) {
                        String message = ColorAPI.apply(ct.getConfig().getString("Messages.No-Longer-Ct"));
                        event.getDamager().sendMessage(message);
                        ct.getCombatManager().getContents().remove(event.getDamager().getUniqueId());
                        cancel();
                    }
                }
            }.runTaskTimer(ct, 0, 20);
        }
    }

    @EventHandler
    public void onLoggerAttack(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Villager target) {
            if (!(event.getDamager() instanceof Player)) return;
            String pdc = PDCUtil.getString(JavaPlugin.getPlugin(CombatTag.class), target, "Owner");
            if (pdc == null) return;

            UUID uuid = UUID.fromString(pdc);
            if (!this.ct.getCombatManager().getContents().containsKey(uuid)) return;

            if (event.getFinalDamage() > ((Villager) event.getEntity()).getHealth()) {
                event.setDamage(0.0D);
                event.setCancelled(true);
                event.getEntity().remove();
                for (ItemStack item : this.ct.getCombatManager().getContents().get(uuid)) {
                    if (event.getEntity().getLocation().getWorld() == null) return;

                    if (item == null) continue;
                    event.getEntity().getLocation().getWorld().dropItemNaturally(event.getEntity().getLocation(), item);
                }
                InvUtil.clearInventory(Bukkit.getOfflinePlayer(uuid));
                this.ct.getCombatManager().getContents().remove(uuid);
                this.ct.getCombatManager().getLogged().remove(uuid);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if (this.ct.getCombatManager().getLogged().containsKey(player.getUniqueId())) {
            Entity entity = this.ct.getCombatManager().getLogged().get(player.getUniqueId());
            player.teleport(entity);
            entity.remove();
            this.ct.getCombatManager().getLogged().remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (this.ct.getCombatManager().getCooldown().asMap().containsKey(event.getPlayer().getUniqueId())) {
            Entity entity = this.ct.getCombatManager().spawnLogger(event.getPlayer());

            final int length = CombatTag.get().getConfig().getInt("Seconds");
            new BukkitRunnable() {

                @Override
                public void run() {
                    if (entity == null) {
                        cancel();
                        return;
                    }
                    if (!entity.isDead()) entity.remove();
                    cancel();
                }
            }.runTaskLater(ct, length * 20L);
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (this.ct.getCombatManager().getCooldown().asMap().containsKey(event.getPlayer().getUniqueId())) {
           for (String cmd : this.ct.getConfig().getStringList("Blacklisted")) {
               if (event.getMessage().contains(cmd)) {
                   event.setCancelled(true);
                   String message = ColorAPI.apply(Objects.requireNonNull(this.ct.getConfig().getString("Messages.On-Ct")));
                   event.getPlayer().sendMessage(message);
               }
           }

        }
    }

}
