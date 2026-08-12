package org.thesplitting.core.service;

import org.thesplitting.core.errorhandler.PlayerErrorHandler;
import org.thesplitting.core.events.player.PlayerListener;
import org.thesplitting.core.item.ItemManager;
import org.thesplitting.core.player.PlayerService;
import org.thesplitting.core.world.WorldService;
import org.thesplitting.data.filemanager.PlayerFileManager;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class ServiceConfig {
    public static List<Function<ServiceRegistry, IService>> getServices() {
        return List.of(
                WorldService::new,
                ItemManager::new,
                PlayerErrorHandler::new,
                registry -> new PlayerFileManager(registry, Path.of("players")),
                registry -> new PlayerService(registry.getService(PlayerFileManager.class), registry.getService(ItemManager.class), registry.getService(PlayerErrorHandler.class)),
                registry -> new PlayerListener(registry, registry.getService(PlayerService.class))
        );
    }
}
