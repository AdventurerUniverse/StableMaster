package net.nperkins.stablemaster.commands.subcommands;

import net.nperkins.stablemaster.StableMaster;
import net.nperkins.stablemaster.commands.CommandInfo;
import net.nperkins.stablemaster.commands.SubCommand;
import net.nperkins.stablemaster.data.Stable;
import net.nperkins.stablemaster.data.StabledHorse;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

public class DelRider extends SubCommand {

    public DelRider() {
        setMinArgs(1);
        setPermission("stablemaster.delrider");
    }

    public void handle(CommandInfo commandInfo) {
        final CommandSender sender = commandInfo.getSender();
        final String riderName = commandInfo.getArg(0);

        StableMaster.getPlugin().getServer().getScheduler().runTaskAsynchronously(StableMaster.getPlugin(), new Runnable() {
                    public void run() {
                        OfflinePlayer rider = StableMaster.getPlugin().getServer().getOfflinePlayer(riderName);
                        if (rider != null && rider.hasPlayedBefore()) {
                            StableMaster.delRiderQueue.put((Player) sender, rider);
                            StableMaster.langMessage(sender, "punch-horse");
                        } else {
                            StableMaster.langMessage(sender, "error.player-not-found");
                        }
                    }
                }
        );
    }

    public void handleInteract(Stable stable, Player player, Horse horse) {
        StabledHorse stabledHorse = stable.getHorse(horse);
        OfflinePlayer rider = StableMaster.delRiderQueue.get(player);

        if (player != horse.getOwner() && !player.hasPermission("stablemaster.bypass")) {
            StableMaster.langMessage(player, "error.not-owner");
        }
        else if (!stabledHorse.isRider(rider)) {
            StableMaster.rawMessage(player, String.format(
                    StableMaster.getLang("command.del-rider.not-rider"), rider.getName()));
        }
        else {
            stabledHorse.delRider(rider);
            StableMaster.rawMessage(player, String.format(
                    StableMaster.getLang("command.del-rider.removed"), rider.getName()));
        }
    }

    public String getDescription() {
        return StableMaster.getLang("command.del-rider.description");
    }

    public String getUsage() {
        return StableMaster.getLang("command.del-rider.usage");
    }
}
