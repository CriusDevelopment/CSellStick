package dev.crius.sellstick.hook.hologram.impl;

import dev.crius.sellstick.SellStickPlugin;
import dev.crius.sellstick.hook.hologram.HologramHook;
import dev.crius.sellstick.util.ChatUtils;
import dev.crius.sellstick.util.ItemUtils;
import dev.crius.sellstick.util.Placeholder;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.UUID;

@RequiredArgsConstructor
public class DecentHologramsHook implements HologramHook {

    private final SellStickPlugin plugin;

    @Override
    public String getName() {
        return "DecentHolograms";
    }

    @Override
    public void spawnHologram(Location location, Material material, int amount) {
        Hologram hologram = DHAPI.createHologram(UUID.randomUUID().toString(), location);

        plugin.getPluginConfig().getStringList("Hologram-Lines").forEach(line ->
                DHAPI.addHologramLine(hologram,
                        ChatUtils.colorLegacy(line,
                                new Placeholder("<item>", ItemUtils.fancyName(material)),
                                new Placeholder("<amount>", String.valueOf(amount))
                        )
                )
        );

        Bukkit.getScheduler().runTaskLater(plugin, hologram::delete, 20);
    }

}
