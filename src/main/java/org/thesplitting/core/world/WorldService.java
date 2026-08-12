package org.thesplitting.core.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.thesplitting.core.service.IService;
import org.thesplitting.core.service.ServiceRegistry;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class WorldService implements IService {
    private final ServiceRegistry registry;

    private static final String WORLD_NAME = "world";
    private Path templatePath;
    private Path worldPath;

    public WorldService(ServiceRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onEnable() {
        Path pluginFolder = registry.getPlugin().getDataFolder().toPath();
        templatePath = pluginFolder.resolve("templates").resolve(WORLD_NAME);
        worldPath = Bukkit.getWorldContainer().toPath().resolve(WORLD_NAME);

        try {
            Files.createDirectories(templatePath);
        } catch (IOException e) {
            throw new RuntimeException("Template couldn't be created!", e);
        }

        if (!Files.exists(templatePath.resolve("level.dat"))) {
            registry.getPlugin().getLogger().warning("===========================================");
            registry.getPlugin().getLogger().warning("KEIN WORLD TEMPLATE GEFUNDEN!");
            registry.getPlugin().getLogger().warning("Bitte kopiere eine Welt nach:");
            registry.getPlugin().getLogger().warning(templatePath.toString());
            registry.getPlugin().getLogger().warning("===========================================");
            return;
        }

        loadFreshWorld();
    }

    @Override
    public void onDisable() {
        World world = Bukkit.getWorld(WORLD_NAME);
        if (world != null) {
            World defaultWorld = Bukkit.getWorlds().get(0);
            world.getPlayers().forEach(p -> p.teleport(defaultWorld.getSpawnLocation()));

            Bukkit.unloadWorld(world, false);
        }
    }

    private void loadFreshWorld() {
        registry.getPlugin().getLogger().info("Loading World...");

        try {
            if (Files.exists(worldPath)) {
                registry.getPlugin().getLogger().warning("Deleting old world...");
                deleteDirectory(worldPath);
            }

            registry.getPlugin().getLogger().info("Copy templates...");
            copyDirectory(templatePath, worldPath);

            Files.deleteIfExists(worldPath.resolve("session.lock"));
            Files.deleteIfExists(worldPath.resolve("uid.dat"));

            registry.getPlugin().getLogger().info("Load world... " + WORLD_NAME);
            World world = Bukkit.createWorld(new WorldCreator(WORLD_NAME));

            if (world != null) {
                world.setAutoSave(false);
                world.setKeepSpawnInMemory(true);

                registry.getPlugin().getLogger().info("World loaded...");
            }
        } catch (IOException e) {
            registry.getPlugin().getLogger().warning("Could not load world!");
            throw new RuntimeException(e);
        }
    }

    private void deleteDirectory(Path directory) throws IOException {
        if (!Files.exists(directory)) return;

        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
           @Override
           public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
               Files.delete(file);
               return FileVisitResult.CONTINUE;
           }

           @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
               Files.delete(dir);
               return FileVisitResult.CONTINUE;
           }
        });
    }

    private void copyDirectory(Path source, Path target) throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path targetDir = target.resolve(source.relativize(dir));
                Files.createDirectories(targetDir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.copy(file, target.resolve(source.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Gibt die Spielwelt zurück.
     */
    public World getGameWorld() {
        return Bukkit.getWorld(WORLD_NAME);
    }
}
