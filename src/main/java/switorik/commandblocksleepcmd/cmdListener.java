package switorik.commandblocksleepcmd;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class cmdListener implements CommandExecutor {

    Main plugin = Main.plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof BlockCommandSender) {

            YamlConfiguration message = Main.message;

            //Exits if given invalid parameters.
            if (args.length < 5) return false;
            if (!isInteger(args[0]) && !isInteger(args[0].replace("~", ""))) {
                sender.sendMessage(Objects.requireNonNull(message.getString("badint")));
                return true;
            }
            if (!isInteger(args[1]) && !isInteger(args[1].replace("~", ""))){
                sender.sendMessage(Objects.requireNonNull(message.getString("badint")));
                return true;
            }
            if (!isInteger(args[2]) && !isInteger(args[2].replace("~", ""))){
                sender.sendMessage(Objects.requireNonNull(message.getString("badint")));
                return true;
            }
            if (!isInteger(args[3])){
                sender.sendMessage(Objects.requireNonNull(message.getString("badtime")));
                return true;
            }
            if (!isMaterial(args[4].toUpperCase())){
                sender.sendMessage(Objects.requireNonNull(message.getString("badmaterial")));
                return true;
            }

            int x;
            int y;
            int z;
            int time = Integer.parseInt(args[3]);
            if (time < 0) return false;

            Block block = ((BlockCommandSender) sender).getBlock();
            Location commandLoc = block.getLocation();

            //Set coordinates
            if (args[0].startsWith("~")) x = commandLoc.getBlockX() + Integer.parseInt(args[0].replace("~", ""));
            else x = Integer.parseInt(args[0]);
            if (args[1].startsWith("~")) y = commandLoc.getBlockY() + Integer.parseInt(args[1].replace("~", ""));
            else y = Integer.parseInt(args[1]);
            if (args[2].startsWith("~")) z = commandLoc.getBlockZ() + Integer.parseInt(args[2].replace("~", ""));
            else z = Integer.parseInt(args[2]);

            Location setBlock = new Location(block.getWorld(), x, y, z);

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(
                    plugin, () -> setBlock.getBlock().setType(Objects.requireNonNull(Material.getMaterial(args[4].toUpperCase()))), time);

            switch (args.length) {
                case 6:
                    if (!isMaterial(args[5].toUpperCase())) return false;
                    //since optional: no error message because it will fire if this fails.
                    Bukkit.getScheduler().scheduleSyncDelayedTask(
                            plugin, () -> setBlock.getBlock().setType(Objects.requireNonNull(Material.getMaterial(args[5].toUpperCase()))), time);
                    return true;
                case 7:
                    if (!isMaterial(args[5].toUpperCase())) return false;
                    if (!isInteger(args[6])) return false;
                    //since optional: no error message because it will fire if this fails.
                    Bukkit.getScheduler().scheduleSyncDelayedTask(
                            plugin, () -> setBlock.getBlock().setType(Objects.requireNonNull(Material.getMaterial(args[5].toUpperCase()))), Integer.parseInt(args[6]));
                    return true;
            }
        } else {
            YamlConfiguration message = Main.message;
                if (sender instanceof Player) {
                    if (sender.hasPermission("cmdblockdelay.use")) {
                        sender.sendMessage(Objects.requireNonNull(message.getString("info")));
                    } else sender.sendMessage(Objects.requireNonNull(message.getString("noperm")));
                } else sender.sendMessage(Objects.requireNonNull(message.getString("ingame")));
        }
        return true;
    }

    private Boolean isInteger(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Boolean isMaterial(String mat) {
        try {
            if(Material.getMaterial(mat) != null) return true;
            else
            return false;

        } catch (Exception e) {
            return false;
        }
    }
}


