package org.thesplitting.src.services.services;

import org.bukkit.ChatColor;
import org.thesplitting.src.data.server.chatcontrol.ChatControlLevel;
import org.thesplitting.src.data.server.chatcontrol.ChatFilterResult;
import org.thesplitting.src.services.ServiceRegistry;
import org.thesplitting.src.services.contracts.IService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

public class ChatControlService implements IService {
    private static final Path FOLDER = Path.of("chat-control");
    private static final String OFFENSIVE_RESOURCE = "chat-control/offensive-words.txt";
    private static final String UNACCEPTABLE_RESOURCE = "chat-control/unacceptable-words.txt";

    private final ServiceRegistry registry;
    private Set<String> offensiveWords = Set.of();
    private Set<String> unacceptableWords = Set.of();

    public ChatControlService(ServiceRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onEnable() {
        registry.getPlugin().saveResource(OFFENSIVE_RESOURCE, false);
        registry.getPlugin().saveResource(UNACCEPTABLE_RESOURCE, false);

        offensiveWords = loadWordList("offensive-words.txt");
        unacceptableWords = loadWordList("unacceptable-words.txt");
    }

    @Override
    public void onDisable() {

    }

    public ChatFilterResult evaluate(String message) {
        String[] tokens = message.toLowerCase(Locale.ROOT).split("[^\\p{L}0-9]+");

        for (String token : tokens) {
            if (unacceptableWords.contains(token)) {
                return new ChatFilterResult(ChatControlLevel.UNACCEPTABLE, token, message);
            }
        }

        for (String token : tokens) {
            if (offensiveWords.contains(token)) {
                return new ChatFilterResult(ChatControlLevel.OFFENSIVE, token, message);
            }
        }

        return new ChatFilterResult(ChatControlLevel.CLEAN, null, message);
    }

    private Set<String> loadWordList(String fileName) {
        Path path = registry.getPlugin().getDataFolder().toPath().resolve(FOLDER).resolve(fileName);
        Set<String> words = new HashSet<String>();

        try {
            for (String line : Files.readAllLines(path)) {
                String trimmed = line.trim().toLowerCase(Locale.ROOT);

                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }

                words.add(trimmed);
            }
        } catch (IOException e) {
            registry.getPlugin().getLogger().warning("Could not load word list from file " + fileName + ": " + e.getMessage());
        }

        return words;
    }

    public static String highlightWord(String message, String word) {
        return message.replaceAll("(?i)\\b" + Pattern.quote(word) + "\\b", ChatColor.YELLOW + "$0" + ChatColor.RESET);
    }
}
