package dev.crius.sellstick.hook.hologram;

import dev.crius.sellstick.hook.Hook;
import org.bukkit.Location;
import org.bukkit.Material;

public interface HologramHook extends Hook {

    void spawnHologram(Location location, Material material, int amount);

}
