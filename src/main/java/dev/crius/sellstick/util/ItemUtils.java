package dev.crius.sellstick.util;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ItemUtils {

    public static String fancyName(Material material) {
        return Arrays.stream(material.name().split("_"))
                .map(t -> t.charAt(0) + t.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

}
