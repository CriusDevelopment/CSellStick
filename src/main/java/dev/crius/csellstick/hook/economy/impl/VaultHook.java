package dev.crius.csellstick.hook.economy.impl;

import dev.crius.csellstick.hook.economy.EconomyHook;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook implements EconomyHook {

    private Economy economy = null;

    @Override
    public String getName() {
        return "Vault";
    }

    @Override
    public void init() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return;

        economy = rsp.getProvider();
    }

    @Override
    public void remove(OfflinePlayer player, double amount) {
        economy.withdrawPlayer(player, amount);
    }

    @Override
    public void add(OfflinePlayer player, double amount) {
        economy.depositPlayer(player, amount);
    }

    @Override
    public double getMoney(OfflinePlayer player) {
        return economy.getBalance(player);
    }

    @Override
    public void setMoney(OfflinePlayer player, double amount) {
        this.remove(player, this.getMoney(player));
        this.add(player, amount);
    }

}
