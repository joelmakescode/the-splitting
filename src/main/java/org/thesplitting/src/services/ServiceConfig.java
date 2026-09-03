package org.thesplitting.src.services;

import org.thesplitting.src.events.item.ItemListenerManager;
import org.thesplitting.src.misc.errorhandler.PlayerErrorHandler;
import org.thesplitting.src.events.item.ItemListener;
import org.thesplitting.src.events.player.PlayerListener;
import org.thesplitting.src.item.ItemManager;
import org.thesplitting.src.services.service.InventoryService;
import org.thesplitting.src.services.service.MessageService;
import org.thesplitting.src.services.service.PlayerService;
import org.thesplitting.src.world.WorldService;
import org.thesplitting.src.filemanager.PlayerFileManager;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class ServiceConfig {
    public static List<Function<ServiceRegistry, IService>> getServices() {
        return List.of(
                ItemManager::new,
                PlayerErrorHandler::new,
                WorldService::new,
                MessageService::new,


                registry -> new PlayerFileManager(registry, Path.of("players")),
                registry -> new PlayerService(registry.getService(PlayerFileManager.class), registry.getService(ItemManager.class) , registry.getService(PlayerErrorHandler.class)),
                registry -> new PlayerListener(registry, registry.getService(PlayerService.class)),

                registry -> new InventoryService(registry.getService(ItemManager.class), registry.getService(PlayerService.class)),

                registry -> new ItemListenerManager(registry.getService(InventoryService.class)),
                registry -> new ItemListener(registry, registry.getService(ItemListenerManager.class))

        );
    }
}
