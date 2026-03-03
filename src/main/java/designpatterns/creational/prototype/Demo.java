package designpatterns.creational.prototype;

import java.util.HashMap;
import java.util.Map;

/**
 * Prototype Pattern
 *
 * Intent: Specify the kinds of objects to create using a prototypical instance,
 * and create new objects by copying this prototype.
 *
 * Example: Shape registry where shapes are cloned from registered prototypes.
 * Java 25: sealed interface + records (immutable, naturally "copyable") +
 *          pattern matching switch for type-safe descriptions.
 */

// ── Prototype (sealed interface) ──────────────────────────────────────────────

sealed interface Shape permits Circle, Rectangle, Triangle {
    Shape copy();
    double area();
    String color();
}

// ── Concrete Prototypes (records — copy() returns a structurally equal instance) ──

record Circle(double radius, String color) implements Shape {
    public Shape copy()    { return new Circle(radius, color); }
    public double area()   { return Math.PI * radius * radius; }
}

record Rectangle(double width, double height, String color) implements Shape {
    public Shape copy()    { return new Rectangle(width, height, color); }
    public double area()   { return width * height; }
}

record Triangle(double base, double height, String color) implements Shape {
    public Shape copy()    { return new Triangle(base, height, color); }
    public double area()   { return 0.5 * base * height; }
}

// ── Prototype Registry ────────────────────────────────────────────────────────

class ShapeRegistry {
    private final Map<String, Shape> prototypes = new HashMap<>();

    public void register(String key, Shape prototype) {
        prototypes.put(key, prototype);
    }

    public Shape clone(String key) {
        var prototype = prototypes.get(key);
        if (prototype == null) throw new IllegalArgumentException("Prototype not found: " + key);
        return prototype.copy();
    }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        var registry = new ShapeRegistry();
        registry.register("red-circle",      new Circle(5.0, "red"));
        registry.register("blue-rectangle",  new Rectangle(10.0, 4.0, "blue"));
        registry.register("green-triangle",  new Triangle(6.0, 8.0, "green"));

        var shapes = new Shape[]{
            registry.clone("red-circle"),
            registry.clone("red-circle"),     // independent clone
            registry.clone("blue-rectangle"),
            registry.clone("green-triangle"),
        };

        System.out.printf("%-42s %s%n", "Shape", "Area");
        System.out.println("-".repeat(55));

        for (var shape : shapes) {
            // Pattern matching switch (Java 21+)
            var description = switch (shape) {
                case Circle c     -> "Circle(r=%.1f, color=%s)".formatted(c.radius(), c.color());
                case Rectangle r  -> "Rectangle(%.1fx%.1f, color=%s)".formatted(r.width(), r.height(), r.color());
                case Triangle t   -> "Triangle(b=%.1f, h=%.1f, color=%s)".formatted(t.base(), t.height(), t.color());
            };
            System.out.printf("%-42s %.2f%n", description, shape.area());
        }
    }
}
