package dev.crius.csellstick.hook.shop.impl;

import com.pablo67340.guishop.api.GuiShopAPI;
import dev.crius.csellstick.hook.shop.ShopHook;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GUIShopHook implements ShopHook {

    @Override
    public String getName() {
        return "GUIShop";
    }

    @Override
    public double getPrice(Material material, double def) {
        double sellPrice = GuiShopAPI.getSellPrice(new ItemStack(material), 1);
        return sellPrice == -1 ? def : sellPrice;
    }

}
