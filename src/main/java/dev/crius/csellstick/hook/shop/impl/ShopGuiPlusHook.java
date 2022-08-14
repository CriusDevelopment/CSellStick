package dev.crius.csellstick.hook.shop.impl;

import dev.crius.csellstick.hook.shop.ShopHook;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ShopGuiPlusHook implements ShopHook {

    @Override
    public String getName() {
        return "ShopGUIPlus";
    }

    @Override
    public double getPrice(Material material, double def) {
        double price = ShopGuiPlusApi.getItemStackPriceSell(new ItemStack(material));
        return price == -1 ? def : price;
    }

}
