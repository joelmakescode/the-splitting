package data.filemanager;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerFileManagerTest {
    /**
    @TempDir
    private Path tempDir;

    @Test
    public void createPlayerFile_createJsonWithPlayerData() throws Exception {
        JavaPlugin plugin = Mockito.mock(JavaPlugin.class);
        when(plugin.getDataFolder()).thenReturn(tempDir.toFile());
        when(plugin.getLogger()).thenReturn(Logger.getLogger("test-logger"));

        ServiceRegistry registry = Mockito.mock(ServiceRegistry.class);
        when(registry.getPlugin()).thenReturn(plugin);

        Path playersFolder = Path.of("players");
        PlayerFileManager fileManager = new PlayerFileManager(registry, playersFolder);

        fileManager.onEnable();

        Player player = Mockito.mock(Player.class);
        UUID uuid = UUID.randomUUID();
        when(player.getUniqueId()).thenReturn(uuid);
        when(player.getName()).thenReturn("test_player");

        fileManager.createPlayerFile(player);

        Path jsonPath = tempDir.resolve("players").resolve(uuid.toString() + ".json");

        assertTrue(Files.exists(jsonPath), "Die JSON-Datei wurde mit Player-Daten erstellt.");

        String content = Files.readString(jsonPath);
        assertTrue(content.contains("\"name\":\"test_player\""));
        //assertTrue(content.contains("\"maxHealth\":4"));
        //assertTrue(content.contains("\"level\":1"));
    } */
}
