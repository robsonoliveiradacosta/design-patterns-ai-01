package designpatterns.behavioral.state;

/**
 * State Pattern
 *
 * Intent: Allow an object to alter its behaviour when its internal state changes.
 * The object will appear to change its class.
 *
 * Example: Traffic light that cycles through Red → Green → Yellow → Red.
 * Java 25: sealed interface for the state hierarchy + records as concrete states
 *          + pattern matching switch for state descriptions.
 */

// ── State Interface (sealed) ──────────────────────────────────────────────────

sealed interface TrafficLightState permits RedState, GreenState, YellowState {
    /** Perform the current state's action and transition to the next state. */
    void handle(TrafficLight light);
    String display();
}

// ── Concrete States (records — stateless, so a single instance is enough) ────

record RedState() implements TrafficLightState {
    public String display() { return "RED    — STOP"; }
    public void handle(TrafficLight light) {
        System.out.println("  " + display() + " → transitioning to GREEN");
        light.setState(new GreenState());
    }
}

record GreenState() implements TrafficLightState {
    public String display() { return "GREEN  — GO"; }
    public void handle(TrafficLight light) {
        System.out.println("  " + display() + " → transitioning to YELLOW");
        light.setState(new YellowState());
    }
}

record YellowState() implements TrafficLightState {
    public String display() { return "YELLOW — SLOW DOWN"; }
    public void handle(TrafficLight light) {
        System.out.println("  " + display() + " → transitioning to RED");
        light.setState(new RedState());
    }
}

// ── Context ───────────────────────────────────────────────────────────────────

class TrafficLight {
    private TrafficLightState state;

    TrafficLight(TrafficLightState initialState) {
        this.state = initialState;
    }

    public void setState(TrafficLightState state) {
        this.state = state;
    }

    /** One tick = one phase of the traffic light cycle. */
    public void tick() {
        System.out.print("[ " + state.display() + " ] ");
        state.handle(this);
    }

    public String currentStateName() {
        return switch (state) {
            case RedState    r -> "Red";
            case GreenState  g -> "Green";
            case YellowState y -> "Yellow";
        };
    }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        var light = new TrafficLight(new RedState());

        System.out.println("Traffic light simulation (6 ticks):");
        for (int i = 1; i <= 6; i++) {
            System.out.printf("Tick %d: ", i);
            light.tick();
        }
    }
}
