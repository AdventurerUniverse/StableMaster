package net.nperkins.stablemaster.commands.subcommands;

import net.nperkins.stablemaster.StableMaster;
import net.nperkins.stablemaster.commandlibs.CommandInfo;
import net.nperkins.stablemaster.commandlibs.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Info extends SubCommand {

    public Info(StableMaster plugin) {
        this.plugin = plugin;
    }

    public void handle(CommandInfo commandInfo) {
        final CommandSender sender = commandInfo.getSender();
        if (sender.hasPermission("stablemaster.info")) {
            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        public void run() {
                            if (!plugin.infoQueue.contains(sender)) {
                                plugin.infoQueue.add((Player) sender);
                            }
                            sender.sendMessage(StableMaster.playerMessage("Punch the horse."));
                        }
                    }
            );
        } else {
            sender.sendMessage(StableMaster.playerMessage("You don't have permission to do this."));
        }
    }

    public String getUsage() {
        return "info";
    };

}
