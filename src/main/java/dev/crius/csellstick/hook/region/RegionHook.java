package dev.crius.csellstick.hook.region;

import dev.crius.csellstick.hook.Hook;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface RegionHook extends Hook {

    boolean canSellChest(Player player, Location location);

}
