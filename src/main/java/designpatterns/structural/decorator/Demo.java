package designpatterns.structural.decorator;

/**
 * Decorator Pattern
 *
 * Intent: Attach additional responsibilities to an object dynamically.
 * Decorators provide a flexible alternative to subclassing for extending
 * functionality.
 *
 * Example: Coffee shop where a base coffee can be decorated with extras
 *          (milk, sugar, whip, vanilla) that each add to description and cost.
 */

// ── Component ─────────────────────────────────────────────────────────────────

interface Coffee {
    String description();
    double cost();
}

// ── Concrete Component ────────────────────────────────────────────────────────

record SimpleCoffee(String roast) implements Coffee {
    public String description() { return roast + " coffee"; }
    public double cost()        { return 2.00; }
}

// ── Base Decorator ────────────────────────────────────────────────────────────

abstract class CoffeeDecorator implements Coffee {
    protected final Coffee wrapped;

    CoffeeDecorator(Coffee wrapped) {
        this.wrapped = wrapped;
    }
}

// ── Concrete Decorators ───────────────────────────────────────────────────────

class MilkDecorator extends CoffeeDecorator {
    MilkDecorator(Coffee wrapped) { super(wrapped); }
    public String description()   { return wrapped.description() + ", milk"; }
    public double cost()          { return wrapped.cost() + 0.50; }
}

class SugarDecorator extends CoffeeDecorator {
    SugarDecorator(Coffee wrapped) { super(wrapped); }
    public String description()    { return wrapped.description() + ", sugar"; }
    public double cost()           { return wrapped.cost() + 0.25; }
}

class WhipDecorator extends CoffeeDecorator {
    WhipDecorator(Coffee wrapped) { super(wrapped); }
    public String description()   { return wrapped.description() + ", whip"; }
    public double cost()          { return wrapped.cost() + 0.75; }
}

class VanillaDecorator extends CoffeeDecorator {
    VanillaDecorator(Coffee wrapped) { super(wrapped); }
    public String description()      { return wrapped.description() + ", vanilla syrup"; }
    public double cost()             { return wrapped.cost() + 1.00; }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    private static void printOrder(Coffee coffee) {
        System.out.printf("%-50s $%.2f%n", coffee.description(), coffee.cost());
    }

    public static void main(String[] args) {
        // Plain espresso
        Coffee espresso = new SimpleCoffee("espresso");

        // Latte = espresso + milk + sugar
        Coffee latte = new SugarDecorator(new MilkDecorator(new SimpleCoffee("medium roast")));

        // Fancy = dark roast + milk + vanilla + whip
        Coffee fancy = new WhipDecorator(
                new VanillaDecorator(
                        new MilkDecorator(new SimpleCoffee("dark roast"))));

        System.out.printf("%-50s %s%n", "Order", "Price");
        System.out.println("-".repeat(58));
        printOrder(espresso);
        printOrder(latte);
        printOrder(fancy);
    }
}
