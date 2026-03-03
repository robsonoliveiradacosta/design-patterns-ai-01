package designpatterns.structural.flyweight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Flyweight Pattern
 *
 * Intent: Use sharing to support large numbers of fine-grained objects
 * efficiently by separating intrinsic (shared) from extrinsic (unique) state.
 *
 * Example: A text editor renders thousands of characters. The style (font, size,
 *          color) is shared (intrinsic); position is unique per character (extrinsic).
 * Java 25: record for CharacterStyle — immutable and a natural flyweight.
 */

// ── Flyweight (intrinsic, shared state) ───────────────────────────────────────

record CharacterStyle(String font, int size, String color) {
    void render(char c, int x, int y) {
        System.out.printf("  '%c' at (%3d,%3d)  [%s %dpt %s]%n", c, x, y, font, size, color);
    }
}

// ── Flyweight Factory ─────────────────────────────────────────────────────────

class CharacterStyleFactory {
    private final Map<String, CharacterStyle> cache = new HashMap<>();

    public CharacterStyle get(String font, int size, String color) {
        var key = "%s-%d-%s".formatted(font, size, color);
        return cache.computeIfAbsent(key, k -> {
            System.out.println("[Factory] Creating new style object: " + k);
            return new CharacterStyle(font, size, color);
        });
    }

    public int cacheSize() { return cache.size(); }
}

// ── Context (extrinsic, unique state) ─────────────────────────────────────────

record CharacterContext(char character, int x, int y, CharacterStyle style) {
    void render() { style.render(character, x, y); }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        var factory = new CharacterStyleFactory();
        var document = new ArrayList<CharacterContext>();

        // Simulate writing "Hello!" with mixed styles
        // Most characters share the same style object
        var normal = factory.get("Arial", 12, "black");
        var bold   = factory.get("Arial-Bold", 12, "black");
        var red    = factory.get("Arial", 14, "red");

        var text = "Hello!";
        for (int i = 0; i < text.length(); i++) {
            CharacterStyle style = switch (text.charAt(i)) {
                case '!' -> red;
                case 'H' -> bold;
                default  -> normal;
            };
            document.add(new CharacterContext(text.charAt(i), i * 10, 0, style));
        }

        // Add a second line reusing the same styles (no new objects created)
        var text2 = "World";
        for (int i = 0; i < text2.length(); i++) {
            document.add(new CharacterContext(text2.charAt(i), i * 10, 20, normal));
        }

        System.out.println("\n--- Rendering document ---");
        document.forEach(CharacterContext::render);

        System.out.printf("%n%d characters rendered using only %d style objects (flyweights)%n",
                document.size(), factory.cacheSize());
    }
}
