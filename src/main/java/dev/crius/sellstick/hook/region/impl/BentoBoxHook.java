package dev.crius.sellstick.hook.region.impl;

import dev.crius.sellstick.SellStickPlugin;
import dev.crius.sellstick.hook.region.RegionHook;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.database.objects.Island;
import world.bentobox.bentobox.managers.IslandsManager;
import world.bentobox.bentobox.managers.RanksManager;

@RequiredArgsConstructor
public class BentoBoxHook implements RegionHook {

    private final IslandsManager islandsManager = BentoBox.getInstance().getIslands();
    private final SellStickPlugin plugin;

    @Override
    public String getName() {
        return "BentoBox";
    }

    @Override
    public boolean canSellChest(Player player, Location location) {
        Island island = islandsManager.getIslandAt(location).orElse(null);

        if (island == null) return false;
        if (island.getOwner() == null) return false;
        if (island.getOwner().equals(player.getUniqueId())) return true;
        if (island.getMemberSet(RanksManager.COOP_RANK).contains(player.getUniqueId())
                && plugin.getPluginConfig().getBoolean("Settings.allow-coops-to-sell")) return true;

        return island.getMemberSet(RanksManager.MEMBER_RANK).contains(player.getUniqueId());
    }

}
