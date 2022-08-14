package dev.crius.csellstick.hook.region.impl;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import dev.crius.csellstick.CSellStickPlugin;
import dev.crius.csellstick.hook.region.RegionHook;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class SuperiorSkyBlockHook implements RegionHook {

    private final CSellStickPlugin plugin;

    @Override
    public String getName() {
        return "SuperiorSkyblock2";
    }

    @Override
    public boolean canSellChest(Player player, Location location) {
        Island island = SuperiorSkyblockAPI.getIslandAt(location);
        SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(player);
        if (island == null || superiorPlayer == null) return false;
        if (island.isCoop(superiorPlayer)
                && plugin.getPluginConfig().getBoolean("Settings.allow-coops-to-sell")) return true;

        // since isMember method checks the owner, we don't need to check for the owner
        return island.isMember(superiorPlayer);
    }

}
