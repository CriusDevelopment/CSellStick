package dev.crius.sellstick.hook.shop;

import dev.crius.sellstick.hook.Hook;
import org.bukkit.Material;

public interface ShopHook extends Hook {

    double getPrice(Material material, double def);

}
