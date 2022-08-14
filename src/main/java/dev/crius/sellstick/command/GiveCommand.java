package dev.crius.sellstick.command;

import dev.crius.sellstick.stick.SellStick;
import dev.crius.sellstick.util.ChatUtils;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.BaseCommand;
import dev.triumphteam.cmd.core.annotation.Command;
import dev.triumphteam.cmd.core.annotation.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("sellstick")
public class GiveCommand extends BaseCommand {

    @SubCommand("give")
    @Permission("crius.sellstick.give")
    public void giveCommand(CommandSender sender, SellStick stick, Player target) {
        target.getInventory().addItem(stick.getStickItem().clone());
        sender.sendMessage(ChatUtils.format("<green>Successfully given a sellstick to player!"));
    }

}
