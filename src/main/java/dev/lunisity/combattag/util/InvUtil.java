package dev.lunisity.combattag.util;

import lombok.SneakyThrows;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.PlayerDataStorage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_20_R3.CraftServer;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class InvUtil {

    @SneakyThrows
    public static void clearInventory(final OfflinePlayer player) {

        PlayerDataStorage storage = ((CraftServer) Bukkit.getServer()).getServer().playerDataStorage;
        final CompoundTag compoundTag = storage.getPlayerData(player.getUniqueId().toString());

        compoundTag.remove("Inventory");


        final File file = File.createTempFile(player.getUniqueId().toString() + "-temp", ".dat", storage.getPlayerDir());
        NbtIo.writeCompressed(compoundTag, file.toPath());

        final File file1 = new File(storage.getPlayerDir(), player.getUniqueId() + ".dat");
        final File file2 = new File(storage.getPlayerDir(), player.getUniqueId() + ".dat_old");

        Util.safeReplaceFile(file1.toPath(), file.toPath(), file2.toPath());
    }

}
