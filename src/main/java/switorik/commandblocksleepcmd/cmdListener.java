package switorik.commandblocksleepcmd;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class cmdListener implements CommandExecutor {

    Main plugin = Main.plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("sleep")) {

            if (sender instanceof BlockCommandSender) {

                if (args.length >= 5) {
                    //format will be /sleep x y z time setblock replacementblock:optional

                    while (true) {

                        int x = 0;
                        int y = 0;
                        int z = 0;
                        int time = 0;
                        Material material = Material.AIR;
                        Material replacement = Material.AIR;
                        Block block = ((BlockCommandSender) sender).getBlock();
                        Location commandLoc = block.getLocation();
                        Location setBlock = new Location(block.getWorld(), x, y, z);
                        //setting up initial variables

                        if (isInteger(args[0])) {
                            x = Integer.parseInt(args[0]);
                        } else {
                            if (args[0].contains("`")) {
                                if (isInteger(args[0].split("`")[1])) {
                                    x = commandLoc.getBlockX() + Integer.parseInt(args[0].split("`")[1]);
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                        if (isInteger(args[1])) {
                            y = Integer.parseInt(args[1]);
                        } else {
                            if (args[1].contains("`")) {
                                if (isInteger(args[1].split("`")[1])) {
                                    y = commandLoc.getBlockY() + Integer.parseInt(args[1].split("`")[1]);
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                        if (isInteger(args[2])) {
                            z = Integer.parseInt(args[2]);
                        } else {
                            if (args[2].contains("`")) {
                                if (isInteger(args[2].split("`")[1])) {
                                    z = commandLoc.getBlockZ() + Integer.parseInt(args[2].split("`")[1]);
                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }

                        if (isInteger(args[3])) {
                            time = Integer.parseInt(args[3]);
                            if (time < 0) {
                                break;
                            }
                        } else {
                            break;
                        }
                        if (isMaterial(args[4])) {
                            material = Material.getMaterial(args[4]);
                        } else {
                            break;
                        }

                        setBlock.setX(x);
                        setBlock.setY(y);
                        setBlock.setZ(z);

                        time = time / 1000;

                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            public void run() {

                                setBlock.getBlock().setType(Material.getMaterial(args[4]));

                            }

                        }, time * 20);

                        if (!args[5].isEmpty()) {

                            if (isMaterial(args[5])) {
                                replacement = Material.getMaterial(args[5]);

                                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                    public void run() {

                                        setBlock.getBlock().setType(Material.getMaterial(args[5]));

                                    }

                                }, (time * 20) + 1);

                            } else {
                                break;
                            }
                        }

                        break;
                    }

                }

            } else {

                YamlConfiguration message = Main.message;

                if (sender instanceof Player) {

                    if (sender.hasPermission("sleep.use")) {

                        sender.sendMessage(message.getString("info"));

                    } else {

                        sender.sendMessage(message.getString("noperm"));

                    }

                } else {

                    sender.sendMessage(message.getString("ingame"));

                }

            }

        }

        return true;
    }

    public Boolean isInteger(String num) {

        Boolean isInt;
        try {
            Integer.parseInt(num);
            isInt = true;
        } catch (Exception e) {
            isInt = false;
        }

        return isInt;
    }

    public Boolean isMaterial(String mat) {

        Boolean isMat;
        try {
            Material.getMaterial(mat);
            isMat = true;
        } catch (Exception e) {
            isMat = false;
        }

        return isMat;
    }

}


