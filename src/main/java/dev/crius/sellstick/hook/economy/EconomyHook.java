package dev.crius.sellstick.hook.economy;

import dev.crius.sellstick.hook.Hook;
import org.bukkit.OfflinePlayer;

public interface EconomyHook extends Hook {

    void remove(OfflinePlayer player, double amount);

    void add(OfflinePlayer player, double amount);

    double getMoney(OfflinePlayer player);

    void setMoney(OfflinePlayer player, double amount);

    default boolean has(OfflinePlayer player, double amount) {
        return getMoney(player) >= amount;
    }

}
