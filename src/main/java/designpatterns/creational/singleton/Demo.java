package designpatterns.creational.singleton;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton Pattern
 *
 * Intent: Ensure a class has only one instance and provide a global point of
 * access to it.
 *
 * Two implementations shown:
 *  1. Enum Singleton     — simplest, thread-safe, serialization-safe (Effective Java)
 *  2. Double-checked locking — classic lazy initialization with volatile
 */

// ── 1. Enum Singleton (recommended) ──────────────────────────────────────────

enum DatabaseConnection {
    INSTANCE;

    private int queryCount = 0;

    public void executeQuery(String sql) {
        queryCount++;
        System.out.printf("[DB #%d] Executing: %s%n", queryCount, sql);
    }

    public int getQueryCount() { return queryCount; }
}

// ── 2. Classic Singleton with double-checked locking ─────────────────────────

class ConfigManager {
    private static volatile ConfigManager instance;
    private final Map<String, String> config = new HashMap<>();

    private ConfigManager() {
        // Simulate loading from a config file
        config.put("host",    "localhost");
        config.put("port",    "8080");
        config.put("env",     "production");
        config.put("timeout", "30s");
        System.out.println("[ConfigManager] Loaded configuration");
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    public String get(String key)                  { return config.getOrDefault(key, "not-found"); }
    public void   set(String key, String value)    { config.put(key, value); }
    public Map<String, String> all()               { return Collections.unmodifiableMap(config); }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        // Enum Singleton
        System.out.println("=== Enum Singleton ===");
        var db1 = DatabaseConnection.INSTANCE;
        var db2 = DatabaseConnection.INSTANCE;
        db1.executeQuery("SELECT * FROM users");
        db2.executeQuery("INSERT INTO orders VALUES (...)");
        System.out.println("Same instance? " + (db1 == db2));

        // Classic Singleton
        System.out.println("\n=== Classic Singleton (double-checked locking) ===");
        var cfg1 = ConfigManager.getInstance();  // triggers construction
        var cfg2 = ConfigManager.getInstance();  // returns cached instance

        cfg1.set("feature-x", "enabled");
        System.out.println("feature-x via cfg2: " + cfg2.get("feature-x"));
        System.out.println("Same instance? " + (cfg1 == cfg2));
        System.out.println("Config: " + cfg1.all());
    }
}
