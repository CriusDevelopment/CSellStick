package dev.crius.sellstick.listener;

import dev.crius.sellstick.SellStickPlugin;
import dev.crius.sellstick.util.ChatUtils;
import dev.crius.sellstick.util.ItemUtils;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class InteractListener implements Listener {

    private final SellStickPlugin plugin;

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (!event.hasBlock()) return;
        if (!event.hasItem()) return;
        if (!plugin.getStickManager().isValidStick(event.getItem())) return;
        if (!(event.getClickedBlock().getState() instanceof Chest chest)) return;
        Player player = event.getPlayer();
        if (!plugin.getRegionHook().canSellChest(player, event.getClickedBlock().getLocation())) return;

        Material material = plugin.getStickManager().getMaterial(event.getItem());

        int amount = 0;

        for (ItemStack content : chest.getBlockInventory().getContents()) {
            if (content != null && content.getType().equals(material)) {
                amount += content.getAmount();
                content.setAmount(0);
            }
        }

        double price = plugin.getShopHook().getPrice(material, 10);
        double total = amount * price;

        plugin.getEconomyHook().add(player, total);

        player.sendMessage(ChatUtils.format(plugin.getPluginConfig().getString("Messages.sold"),
                Placeholder.unparsed("item", ItemUtils.fancyName(material)),
                Placeholder.unparsed("amount", String.valueOf(amount))
        ));

        plugin.getHologramHook().spawnHologram(
                event.getClickedBlock().getLocation().clone().add(0.5, 1.5, 0.5),
                material,
                amount
        );
    }

}
