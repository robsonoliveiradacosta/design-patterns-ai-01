package designpatterns.behavioral.interpreter;

import java.util.Map;

/**
 * Interpreter Pattern
 *
 * Intent: Given a language, define a representation for its grammar along with
 * an interpreter that uses the representation to interpret sentences in the language.
 *
 * Example: Simple arithmetic expression interpreter supporting +, -, *.
 * Java 25: sealed interface for the AST + records for each node type +
 *          pattern matching switch for evaluation.
 */

// ── Abstract Expression (sealed) ──────────────────────────────────────────────

sealed interface Expression permits
        NumberExpr, VariableExpr, AddExpr, SubtractExpr, MultiplyExpr {

    int interpret(Map<String, Integer> context);
}

// ── Terminal Expressions ──────────────────────────────────────────────────────

record NumberExpr(int value) implements Expression {
    public int interpret(Map<String, Integer> ctx) { return value; }
}

record VariableExpr(String name) implements Expression {
    public int interpret(Map<String, Integer> ctx) {
        if (!ctx.containsKey(name)) throw new IllegalArgumentException("Undefined variable: " + name);
        return ctx.get(name);
    }
}

// ── Non-terminal Expressions ──────────────────────────────────────────────────

record AddExpr(Expression left, Expression right) implements Expression {
    public int interpret(Map<String, Integer> ctx) {
        return left.interpret(ctx) + right.interpret(ctx);
    }
}

record SubtractExpr(Expression left, Expression right) implements Expression {
    public int interpret(Map<String, Integer> ctx) {
        return left.interpret(ctx) - right.interpret(ctx);
    }
}

record MultiplyExpr(Expression left, Expression right) implements Expression {
    public int interpret(Map<String, Integer> ctx) {
        return left.interpret(ctx) * right.interpret(ctx);
    }
}

// ── Pretty-printer using pattern matching switch ──────────────────────────────

class ExpressionPrinter {
    static String print(Expression expr) {
        return switch (expr) {
            case NumberExpr   e -> String.valueOf(e.value());
            case VariableExpr e -> e.name();
            case AddExpr      e -> "(" + print(e.left()) + " + " + print(e.right()) + ")";
            case SubtractExpr e -> "(" + print(e.left()) + " - " + print(e.right()) + ")";
            case MultiplyExpr e -> "(" + print(e.left()) + " * " + print(e.right()) + ")";
        };
    }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        // Represents: (x + 5) * (y - 2)
        Expression expr = new MultiplyExpr(
                new AddExpr(new VariableExpr("x"), new NumberExpr(5)),
                new SubtractExpr(new VariableExpr("y"), new NumberExpr(2))
        );

        System.out.println("Expression: " + ExpressionPrinter.print(expr));

        var ctx1 = Map.of("x", 3, "y", 7);
        // (3 + 5) * (7 - 2) = 8 * 5 = 40
        System.out.println("Context " + ctx1 + " → " + expr.interpret(ctx1));

        var ctx2 = Map.of("x", 10, "y", 4);
        // (10 + 5) * (4 - 2) = 15 * 2 = 30
        System.out.println("Context " + ctx2 + " → " + expr.interpret(ctx2));

        // Represents: (a * b) + (a - 1)
        Expression expr2 = new AddExpr(
                new MultiplyExpr(new VariableExpr("a"), new VariableExpr("b")),
                new SubtractExpr(new VariableExpr("a"), new NumberExpr(1))
        );

        var ctx3 = Map.of("a", 4, "b", 3);
        // (4 * 3) + (4 - 1) = 12 + 3 = 15
        System.out.println("\nExpression: " + ExpressionPrinter.print(expr2));
        System.out.println("Context " + ctx3 + " → " + expr2.interpret(ctx3));
    }
}
