package dev.crius.csellstick;

import dev.crius.csellstick.command.GiveCommand;
import dev.crius.csellstick.config.Config;
import dev.crius.csellstick.hook.economy.EconomyHook;
import dev.crius.csellstick.hook.economy.impl.PlayerPointsHook;
import dev.crius.csellstick.hook.economy.impl.VaultHook;
import dev.crius.csellstick.hook.hologram.HologramHook;
import dev.crius.csellstick.hook.hologram.impl.DecentHologramsHook;
import dev.crius.csellstick.hook.hologram.impl.HolographicDisplaysHook;
import dev.crius.csellstick.hook.region.RegionHook;
import dev.crius.csellstick.hook.region.impl.BentoBoxHook;
import dev.crius.csellstick.hook.region.impl.SuperiorSkyBlockHook;
import dev.crius.csellstick.hook.shop.ShopHook;
import dev.crius.csellstick.hook.shop.impl.GUIShopHook;
import dev.crius.csellstick.hook.shop.impl.ShopGuiPlusHook;
import dev.crius.csellstick.listener.InteractListener;
import dev.crius.csellstick.stick.SellStick;
import dev.crius.csellstick.stick.StickManager;
import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Locale;

@Getter
public final class CSellStickPlugin extends JavaPlugin {

    private final Config pluginConfig = new Config(this, "config.yml");
    private final StickManager stickManager = new StickManager(this);

    private ShopHook shopHook;
    private HologramHook hologramHook;
    private RegionHook regionHook;
    private EconomyHook economyHook;

    private BukkitCommandManager<CommandSender> commandManager;

    @Override
    public void onEnable() {

        this.commandManager = BukkitCommandManager.create(this);
        commandManager.registerArgument(SellStick.class, (sender, s) -> this.stickManager.getStick(s));
        commandManager.registerSuggestion(SellStick.class, (sender, context) -> {
            Collection<SellStick> sticks = this.stickManager.getSticks();
            return sticks.stream().map(stick -> stick.getSellMaterial().name())
                    .filter(e -> e.startsWith(context.getArgs().get(0).toUpperCase(Locale.ENGLISH)))
                    .toList();
        });
        commandManager.registerCommand(new GiveCommand());

        pluginConfig.create();

        stickManager.init();

        this.getServer().getPluginManager().registerEvents(new InteractListener(this), this);

        setupHooks();
    }

    public void setupHooks() {
        PluginManager pluginManager = this.getServer().getPluginManager();

        if (pluginManager.getPlugin("HolographicDisplays") != null)
            hologramHook = new HolographicDisplaysHook(this);
        else if (pluginManager.getPlugin("DecentHolograms") != null)
            hologramHook = new DecentHologramsHook(this);

        if (pluginManager.getPlugin("Vault") != null)
            economyHook = new VaultHook();
        else if (pluginManager.getPlugin("PlayerPoints") != null)
            economyHook = new PlayerPointsHook();

        if (pluginManager.getPlugin("ShopGUIPlus") != null)
            shopHook = new ShopGuiPlusHook();
        else if (pluginManager.getPlugin("GUIShop") != null)
            shopHook = new GUIShopHook();

        if (pluginManager.getPlugin("BentoBox") != null)
            regionHook = new BentoBoxHook(this);
        else if (pluginManager.getPlugin("SuperiorSkyblock2") != null)
            regionHook = new SuperiorSkyBlockHook(this);

        if (regionHook == null || shopHook == null || economyHook == null || hologramHook == null) {
            this.getLogger().severe("Could not setup correctly, please install required plugins.");
            pluginManager.disablePlugin(this);
        }
    }

}
