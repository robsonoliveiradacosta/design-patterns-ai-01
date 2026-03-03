package designpatterns.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer Pattern
 *
 * Intent: Define a one-to-many dependency between objects so that when one
 * object changes state, all its dependents are notified and updated automatically.
 *
 * Example: Stock market where multiple observers (mobile app, dashboard, alert
 *          system) react to price changes.
 * Java 25: record for the event payload (immutable, value-based).
 */

// ── Event (record) ────────────────────────────────────────────────────────────

record StockEvent(String symbol, double price, double change) {
    String direction() { return change >= 0 ? "▲" : "▼"; }

    @Override
    public String toString() {
        return "%s: $%.2f (%s%.2f)".formatted(symbol, price, direction(), Math.abs(change));
    }
}

// ── Observer ──────────────────────────────────────────────────────────────────

interface StockObserver {
    void onPriceChanged(StockEvent event);
    String name();
}

// ── Subject ───────────────────────────────────────────────────────────────────

class StockMarket {
    private final List<StockObserver> observers = new ArrayList<>();

    public void subscribe(StockObserver observer) {
        observers.add(observer);
        System.out.println("[Market] " + observer.name() + " subscribed");
    }

    public void unsubscribe(StockObserver observer) {
        observers.remove(observer);
        System.out.println("[Market] " + observer.name() + " unsubscribed");
    }

    public void updatePrice(String symbol, double price, double change) {
        var event = new StockEvent(symbol, price, change);
        System.out.println("\n[Market] Price update → " + event);
        observers.forEach(o -> o.onPriceChanged(event));
    }
}

// ── Concrete Observers ────────────────────────────────────────────────────────

class MobileApp implements StockObserver {
    private final String username;
    MobileApp(String username) { this.username = username; }

    public String name() { return "MobileApp(" + username + ")"; }
    public void onPriceChanged(StockEvent e) {
        System.out.println("  [Push → " + username + "] " + e);
    }
}

class TradingDashboard implements StockObserver {
    private final String dashboardId;
    TradingDashboard(String id) { this.dashboardId = id; }

    public String name() { return "Dashboard(" + dashboardId + ")"; }
    public void onPriceChanged(StockEvent e) {
        System.out.println("  [Dashboard:" + dashboardId + "] Refreshing chart for " + e);
    }
}

class PriceAlertSystem implements StockObserver {
    private final double threshold;   // alert if |change| >= threshold

    PriceAlertSystem(double threshold) { this.threshold = threshold; }
    public String name() { return "AlertSystem(threshold=" + threshold + ")"; }

    public void onPriceChanged(StockEvent e) {
        if (Math.abs(e.change()) >= threshold) {
            System.out.printf("  [ALERT] Large movement detected: %s%n", e);
        }
    }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        var market    = new StockMarket();
        var mobile    = new MobileApp("Alice");
        var dashboard = new TradingDashboard("Room-A");
        var alerts    = new PriceAlertSystem(5.0);

        market.subscribe(mobile);
        market.subscribe(dashboard);
        market.subscribe(alerts);

        market.updatePrice("AAPL", 150.25,  2.50);
        market.updatePrice("TSLA", 200.00, -12.00);  // triggers alert

        System.out.println();
        market.unsubscribe(mobile);
        market.updatePrice("GOOG", 140.00,  1.00);   // mobile no longer notified
    }
}
