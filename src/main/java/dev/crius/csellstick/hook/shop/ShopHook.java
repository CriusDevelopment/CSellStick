package dev.crius.csellstick.hook.shop;

import dev.crius.csellstick.hook.Hook;
import org.bukkit.Material;

public interface ShopHook extends Hook {

    double getPrice(Material material, double def);

}
