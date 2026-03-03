package designpatterns.behavioral.visitor;

import java.util.List;

/**
 * Visitor Pattern
 *
 * Intent: Represent an operation to be performed on the elements of an object
 * structure. Visitor lets you define a new operation without changing the classes
 * of the elements on which it operates.
 *
 * Example: Geometric shapes accept different visitors (area calculator,
 *          perimeter calculator, SVG renderer) without modifying shape classes.
 * Java 25: sealed interface for shapes + records + pattern matching switch
 *          inside the visitor's dispatch method.
 */

// ── Element hierarchy (sealed) ────────────────────────────────────────────────

sealed interface Shape permits Circle, Rectangle, Triangle {
    <T> T accept(ShapeVisitor<T> visitor);
}

record Circle(double radius) implements Shape {
    public <T> T accept(ShapeVisitor<T> visitor) { return visitor.visitCircle(this); }
}

record Rectangle(double width, double height) implements Shape {
    public <T> T accept(ShapeVisitor<T> visitor) { return visitor.visitRectangle(this); }
}

record Triangle(double base, double height) implements Shape {
    public <T> T accept(ShapeVisitor<T> visitor) { return visitor.visitTriangle(this); }
}

// ── Visitor Interface ─────────────────────────────────────────────────────────

interface ShapeVisitor<T> {
    T visitCircle(Circle circle);
    T visitRectangle(Rectangle rectangle);
    T visitTriangle(Triangle triangle);

    // Convenience dispatch using pattern matching switch (Java 21+)
    default T visit(Shape shape) {
        return switch (shape) {
            case Circle    c -> visitCircle(c);
            case Rectangle r -> visitRectangle(r);
            case Triangle  t -> visitTriangle(t);
        };
    }
}

// ── Concrete Visitors ─────────────────────────────────────────────────────────

class AreaVisitor implements ShapeVisitor<Double> {
    public Double visitCircle(Circle c)       { return Math.PI * c.radius() * c.radius(); }
    public Double visitRectangle(Rectangle r) { return r.width() * r.height(); }
    public Double visitTriangle(Triangle t)   { return 0.5 * t.base() * t.height(); }
}

class PerimeterVisitor implements ShapeVisitor<Double> {
    public Double visitCircle(Circle c)       { return 2 * Math.PI * c.radius(); }
    public Double visitRectangle(Rectangle r) { return 2 * (r.width() + r.height()); }
    public Double visitTriangle(Triangle t)   {
        // Assumes right-angled triangle
        return t.base() + t.height() + Math.hypot(t.base(), t.height());
    }
}

class SvgVisitor implements ShapeVisitor<String> {
    public String visitCircle(Circle c) {
        return "<circle r=\"%.1f\" />".formatted(c.radius());
    }
    public String visitRectangle(Rectangle r) {
        return "<rect width=\"%.1f\" height=\"%.1f\" />".formatted(r.width(), r.height());
    }
    public String visitTriangle(Triangle t) {
        return "<polygon points=\"0,%.1f %.1f,%.1f %.1f,%.1f\" />".formatted(
                t.height(), t.base() / 2, 0.0, t.base(), t.height());
    }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        var shapes = List.<Shape>of(
                new Circle(5),
                new Rectangle(4, 6),
                new Triangle(3, 4)
        );

        var area      = new AreaVisitor();
        var perimeter = new PerimeterVisitor();
        var svg       = new SvgVisitor();

        System.out.printf("%-25s %10s %12s%n", "Shape", "Area", "Perimeter");
        System.out.println("-".repeat(50));

        for (var shape : shapes) {
            System.out.printf("%-25s %10.2f %12.2f%n",
                    shape.getClass().getSimpleName(),
                    area.visit(shape),
                    perimeter.visit(shape));
        }

        System.out.println("\n=== SVG Output ===");
        shapes.forEach(s -> System.out.println(svg.visit(s)));
    }
}
