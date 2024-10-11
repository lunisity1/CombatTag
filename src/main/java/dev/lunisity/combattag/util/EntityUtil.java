package dev.lunisity.combattag.util;

import dev.lunisity.combattag.CombatTag;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.List;

public class EntityUtil {

    public static void remove() {
        List<World> worlds = Bukkit.getWorlds();
        for (World wrld : worlds) {
            List<Entity> entities = wrld.getEntities();
            for (Entity ent : entities) {
                if (!CombatTag.get().getCombatManager().getLogged().containsValue(ent)) {
                    continue;
                }
                ent.remove();
            }
        }
    }

    public static void remove(final int id) {
        List<World> worlds = Bukkit.getWorlds();
        for (World wrld : worlds) {
            List<Entity> entities = wrld.getEntities();
            for (Entity ent : entities) {
                if (ent.getEntityId() != id){
                    continue;
                }
                ent.remove();
            }
        }
    }

}
