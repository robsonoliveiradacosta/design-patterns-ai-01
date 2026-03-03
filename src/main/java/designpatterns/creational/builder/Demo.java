package designpatterns.creational.builder;

/**
 * Builder Pattern
 *
 * Intent: Separate the construction of a complex object from its representation,
 * allowing the same construction process to create different representations.
 *
 * Example: Fluent builder for a Pizza with optional toppings.
 * Java 25: record used as the final immutable product.
 */

// ── Product (immutable record) ────────────────────────────────────────────────

record Pizza(
        String size,
        String crust,
        String sauce,
        boolean cheese,
        boolean pepperoni,
        boolean mushrooms,
        boolean olives
) {
    @Override
    public String toString() {
        var toppings = new StringBuilder();
        if (cheese)    toppings.append("cheese ");
        if (pepperoni) toppings.append("pepperoni ");
        if (mushrooms) toppings.append("mushrooms ");
        if (olives)    toppings.append("olives ");

        return """
               Pizza { size=%s, crust=%s, sauce=%s, toppings=[%s]}""".formatted(
                size, crust, sauce, toppings.toString().trim());
    }
}

// ── Builder ───────────────────────────────────────────────────────────────────

class PizzaBuilder {
    private String  size       = "medium";
    private String  crust      = "thin";
    private String  sauce      = "tomato";
    private boolean cheese     = false;
    private boolean pepperoni  = false;
    private boolean mushrooms  = false;
    private boolean olives     = false;

    public PizzaBuilder size(String size)   { this.size  = size;  return this; }
    public PizzaBuilder crust(String crust) { this.crust = crust; return this; }
    public PizzaBuilder sauce(String sauce) { this.sauce = sauce; return this; }
    public PizzaBuilder cheese()            { this.cheese     = true; return this; }
    public PizzaBuilder pepperoni()         { this.pepperoni  = true; return this; }
    public PizzaBuilder mushrooms()         { this.mushrooms  = true; return this; }
    public PizzaBuilder olives()            { this.olives     = true; return this; }

    public Pizza build() {
        return new Pizza(size, crust, sauce, cheese, pepperoni, mushrooms, olives);
    }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        var margherita = new PizzaBuilder()
                .size("large")
                .crust("thin")
                .cheese()
                .build();

        var meatLovers = new PizzaBuilder()
                .size("XL")
                .crust("thick")
                .sauce("bbq")
                .cheese()
                .pepperoni()
                .build();

        var veggie = new PizzaBuilder()
                .size("medium")
                .sauce("pesto")
                .cheese()
                .mushrooms()
                .olives()
                .build();

        System.out.println("Margherita : " + margherita);
        System.out.println("Meat Lovers: " + meatLovers);
        System.out.println("Veggie     : " + veggie);
    }
}
