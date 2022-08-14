package dev.crius.csellstick.stick;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import dev.crius.csellstick.CSellStickPlugin;
import dev.crius.csellstick.util.ChatUtils;
import dev.crius.csellstick.util.ItemUtils;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class StickManager {

    private final Map<Material, SellStick> sellStickMap = new HashMap<>();
    private final CSellStickPlugin plugin;

    public void init() {
        sellStickMap.clear();

        ConfigurationSection section = plugin.getPluginConfig().getConfigurationSection("Sell-Sticks");
        if (section == null) throw new IllegalStateException("Sticks did not setup correctly.");

        for (String key : section.getKeys(false)) {
            Material material = Material.matchMaterial(section.getString(key + ".material", "DIAMOND"));
            if (material == null) throw new IllegalStateException("Sticks did not setup correctly.");

            ItemStack item = ItemBuilder.from(Material.BLAZE_ROD)
                    .name(ChatUtils.format(section.getString(key + ".displayName"),
                            Placeholder.unparsed("<material>", ItemUtils.fancyName(material))))
                    .lore(ChatUtils.format(section.getStringList(key + ".lore"),
                            Placeholder.unparsed("<material>", ItemUtils.fancyName(material))))
                    .glow()
                    .setNbt("crius:sellstick", true)
                    .setNbt("crius:sellstick:material", material.name())
                    .build();

            sellStickMap.put(material, new SellStick(material, item));
        }
    }

    public boolean isValid(Material material) {
        return this.sellStickMap.containsKey(material);
    }

    public Material getMaterial(ItemStack stick) {
        if (stick == null) return null;

        return Material.valueOf(stick.getItemMeta().getPersistentDataContainer()
                .get(new NamespacedKey(plugin, "crius:sellstick:material"), PersistentDataType.STRING));
    }

    public boolean isValidStick(ItemStack stick) {
        if (stick == null) return false;
        if (stick.getType() != Material.BLAZE_ROD) return false;
        if (stick.getItemMeta() == null) return false;
        if (!stick.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "crius:sellstick"))) return false;

        Material material = Material.valueOf(stick.getItemMeta().getPersistentDataContainer()
                .get(new NamespacedKey(plugin, "crius:sellstick:material"), PersistentDataType.STRING));

        return this.isValid(material);
    }

    public SellStick getStick(String s) {
        Material material = Material.matchMaterial(s);
        if (material == null) return null;

        return this.sellStickMap.get(material);
    }

    public SellStick getStick(Material material) {
        return this.sellStickMap.get(material);
    }

    public ImmutableList<SellStick> getSticks() {
        return ImmutableList.copyOf(this.sellStickMap.values());
    }

}
