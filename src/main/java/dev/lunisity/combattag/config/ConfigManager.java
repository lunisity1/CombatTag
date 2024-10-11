package dev.lunisity.combattag.config;

import dev.lunisity.combattag.CombatTag;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    public YamlConfiguration load(final String name) {
        File file = new File(CombatTag.get().getDataFolder(), name);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            CombatTag.get().saveResource(file.getName(), false);
        }

        return YamlConfiguration.loadConfiguration(file);
    }

}
