package designpatterns.structural.bridge;

/**
 * Bridge Pattern
 *
 * Intent: Decouple an abstraction from its implementation so that the two can
 * vary independently.
 *
 * Example: Shapes (abstraction) are rendered by different backends (implementation).
 *          Adding a new shape or a new renderer requires no changes to the other side.
 */

// ── Implementor (rendering backend) ──────────────────────────────────────────

interface Renderer {
    void renderCircle(double x, double y, double radius);
    void renderRectangle(double x, double y, double width, double height);
}

// ── Concrete Implementors ─────────────────────────────────────────────────────

class VectorRenderer implements Renderer {
    public void renderCircle(double x, double y, double radius) {
        System.out.printf("[Vector] circle    at (%.1f, %.1f) r=%.1f%n", x, y, radius);
    }
    public void renderRectangle(double x, double y, double width, double height) {
        System.out.printf("[Vector] rectangle at (%.1f, %.1f) %.1f×%.1f%n", x, y, width, height);
    }
}

class RasterRenderer implements Renderer {
    public void renderCircle(double x, double y, double radius) {
        System.out.printf("[Raster] rasterizing circle    at (%.1f, %.1f) r=%.1f%n", x, y, radius);
    }
    public void renderRectangle(double x, double y, double width, double height) {
        System.out.printf("[Raster] rasterizing rectangle at (%.1f, %.1f) %.1f×%.1f%n", x, y, width, height);
    }
}

// ── Abstraction ───────────────────────────────────────────────────────────────

abstract class Shape {
    protected final Renderer renderer;

    Shape(Renderer renderer) {
        this.renderer = renderer;
    }

    abstract void draw();
    abstract void resize(double factor);
}

// ── Refined Abstractions ──────────────────────────────────────────────────────

class Circle extends Shape {
    private final double x, y;
    private double radius;

    Circle(Renderer renderer, double x, double y, double radius) {
        super(renderer);
        this.x = x; this.y = y; this.radius = radius;
    }

    public void draw()                  { renderer.renderCircle(x, y, radius); }
    public void resize(double factor)   { radius *= factor; }
}

class Rectangle extends Shape {
    private final double x, y;
    private double width, height;

    Rectangle(Renderer renderer, double x, double y, double width, double height) {
        super(renderer);
        this.x = x; this.y = y; this.width = width; this.height = height;
    }

    public void draw()                  { renderer.renderRectangle(x, y, width, height); }
    public void resize(double factor)   { width *= factor; height *= factor; }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        System.out.println("=== Vector Renderer ===");
        var vc = new Circle(new VectorRenderer(), 5, 5, 10);
        vc.draw();
        vc.resize(2);
        vc.draw();

        System.out.println("\n=== Raster Renderer ===");
        var rr = new Rectangle(new RasterRenderer(), 0, 0, 20, 10);
        rr.draw();
        rr.resize(0.5);
        rr.draw();

        System.out.println("\n=== Mix: Circle with Raster, Rectangle with Vector ===");
        new Circle(new RasterRenderer(), 3, 3, 7).draw();
        new Rectangle(new VectorRenderer(), 1, 1, 15, 8).draw();
    }
}
