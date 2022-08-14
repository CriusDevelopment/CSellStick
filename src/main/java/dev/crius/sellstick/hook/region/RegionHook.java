package dev.crius.sellstick.hook.region;

import dev.crius.sellstick.hook.Hook;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface RegionHook extends Hook {

    boolean canSellChest(Player player, Location location);

}
