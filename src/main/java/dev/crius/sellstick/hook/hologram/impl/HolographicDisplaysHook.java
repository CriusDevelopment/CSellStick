package dev.crius.sellstick.hook.hologram.impl;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import dev.crius.sellstick.SellStickPlugin;
import dev.crius.sellstick.hook.hologram.HologramHook;
import dev.crius.sellstick.util.ChatUtils;
import dev.crius.sellstick.util.ItemUtils;
import dev.crius.sellstick.util.Placeholder;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

@RequiredArgsConstructor
public class HolographicDisplaysHook implements HologramHook {

    private final SellStickPlugin plugin;

    @Override
    public String getName() {
        return "HolographicDisplays";
    }

    @Override
    public void spawnHologram(Location location, Material material, int amount) {
        Hologram hologram = HologramsAPI.createHologram(plugin, location);

        plugin.getPluginConfig().getStringList("Hologram-Lines").forEach(line ->
                hologram.appendTextLine(
                        ChatUtils.colorLegacy(line,
                                new Placeholder("<item>", ItemUtils.fancyName(material)),
                                new Placeholder("<amount>", String.valueOf(amount))
                        )
                )
        );

        Bukkit.getScheduler().runTaskLater(plugin, hologram::delete, 20);
    }
}
