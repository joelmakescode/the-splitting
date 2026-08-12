package org.thesplitting;
import org.bukkit.plugin.java.JavaPlugin;
import org.thesplitting.core.service.ServiceRegistry;

public class Plugin extends JavaPlugin {
    private static Plugin plugin;
    private ServiceRegistry serviceRegistry;

    @Override
    public void onEnable() {
        plugin = this;

        // Plugin-Start-Logik
        getLogger().info("=================================");
        getLogger().info("The Splitting wird geladen...");
        getLogger().info("=================================");

        serviceRegistry = new ServiceRegistry(this);
        serviceRegistry.registerAll();
        serviceRegistry.enableAll();

        getLogger().info("Plugin erfolgreich aktiviert!");
    }

    @Override
    public void onDisable() {
        getLogger().info("The Splitting wird deaktiviert...");

        if (serviceRegistry != null) {
            serviceRegistry.disableAll();
        }

        getLogger().info("Plugin deaktiviert!");
    }
}