package dev.crius.csellstick.stick;

import lombok.Data;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Data
public class SellStick {

    private final Material sellMaterial;
    private final ItemStack stickItem;

}
