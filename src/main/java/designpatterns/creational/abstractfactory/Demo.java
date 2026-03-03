package designpatterns.creational.abstractfactory;

/**
 * Abstract Factory Pattern
 *
 * Intent: Provide an interface for creating families of related or dependent
 * objects without specifying their concrete classes.
 *
 * Example: UI widget factory that produces themed components (Light/Dark).
 */

// ── Abstract Products ────────────────────────────────────────────────────────

interface Button {
    void render();
}

interface Checkbox {
    void render();
}

// ── Concrete Products: Light Theme ───────────────────────────────────────────

record LightButton(String label) implements Button {
    public void render() {
        System.out.println("[ " + label + " ]  (Light Button)");
    }
}

record LightCheckbox(boolean checked) implements Checkbox {
    public void render() {
        System.out.println("[" + (checked ? "x" : " ") + "] (Light Checkbox)");
    }
}

// ── Concrete Products: Dark Theme ────────────────────────────────────────────

record DarkButton(String label) implements Button {
    public void render() {
        System.out.println("▓ " + label + " ▓  (Dark Button)");
    }
}

record DarkCheckbox(boolean checked) implements Checkbox {
    public void render() {
        System.out.println("◾" + (checked ? "✓" : " ") + "◾ (Dark Checkbox)");
    }
}

// ── Abstract Factory ─────────────────────────────────────────────────────────

interface UIFactory {
    Button createButton(String label);
    Checkbox createCheckbox(boolean checked);
}

// ── Concrete Factories ────────────────────────────────────────────────────────

class LightThemeFactory implements UIFactory {
    public Button createButton(String label)       { return new LightButton(label); }
    public Checkbox createCheckbox(boolean checked) { return new LightCheckbox(checked); }
}

class DarkThemeFactory implements UIFactory {
    public Button createButton(String label)       { return new DarkButton(label); }
    public Checkbox createCheckbox(boolean checked) { return new DarkCheckbox(checked); }
}

// ── Client ────────────────────────────────────────────────────────────────────

class Application {
    private final UIFactory factory;

    Application(UIFactory factory) {
        this.factory = factory;
    }

    void renderUI() {
        var button   = factory.createButton("Submit");
        var checkbox = factory.createCheckbox(true);
        button.render();
        checkbox.render();
    }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        System.out.println("=== Light Theme ===");
        new Application(new LightThemeFactory()).renderUI();

        System.out.println("\n=== Dark Theme ===");
        new Application(new DarkThemeFactory()).renderUI();
    }
}
