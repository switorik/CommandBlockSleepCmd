package switorik.commandblocksleepcmd;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    public static Main plugin;
    public static YamlConfiguration message;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        this.getCommand("cmdblockdelay").setExecutor(new cmdListener());
        getServer().getLogger().info("Adding command block delay functionality to the server.");

        File messagesyml = new File(plugin.getDataFolder() + File.separator + "messages.yml");
        if(messagesyml.exists()) {
            message = YamlConfiguration.loadConfiguration(messagesyml);
        } else {
            plugin.saveResource("messages.yml", false);
        }
        File helptxt = new File(plugin.getDataFolder() + File.separator + "messages.txt");
        if(helptxt.exists()) {
            message = YamlConfiguration.loadConfiguration(helptxt);
        } else {
            plugin.saveResource("help.txt", false);
        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getLogger().info("Removing command block delay functionality to the server.");
    }
}
