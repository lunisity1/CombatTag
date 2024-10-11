package dev.lunisity.combattag;

import dev.lunisity.combattag.config.ConfigManager;
import dev.lunisity.combattag.listener.CombatListener;
import dev.lunisity.combattag.manager.CombatManager;
import dev.lunisity.combattag.util.EntityUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class CombatTag extends JavaPlugin {

    private static CombatTag instance;
    private ConfigManager configManager;
    public YamlConfiguration config;
    public CombatManager combatManager;

    @Override
    public void onEnable() {
        instance = this;
        configManager = new ConfigManager();
        config = configManager.load("config.yml");
        combatManager = new CombatManager(this);

        Bukkit.getPluginManager().registerEvents(new CombatListener(this), this);
    }

    public static CombatTag get() {
        return instance;
    }

    @Override
    public void onDisable() {
        EntityUtil.remove();
    }
}
