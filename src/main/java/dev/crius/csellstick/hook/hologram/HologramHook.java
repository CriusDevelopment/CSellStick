package dev.crius.csellstick.hook.hologram;

import dev.crius.csellstick.hook.Hook;
import org.bukkit.Location;
import org.bukkit.Material;

public interface HologramHook extends Hook {

    void spawnHologram(Location location, Material material, int amount);

}
