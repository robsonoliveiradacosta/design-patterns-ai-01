package designpatterns.behavioral.command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Command Pattern
 *
 * Intent: Encapsulate a request as an object, thereby letting you parameterize
 * clients with different requests, queue or log requests, and support undoable
 * operations.
 *
 * Example: Smart-light remote control that supports executing and undoing commands.
 * Java 25: records for concrete commands (immutable, carry their own undo state).
 */

// ── Receiver ──────────────────────────────────────────────────────────────────

class SmartLight {
    private boolean on         = false;
    private int     brightness = 100;

    public void turnOn()                  { on = true;  System.out.println("  Light: ON"); }
    public void turnOff()                 { on = false; System.out.println("  Light: OFF"); }
    public void setBrightness(int level)  { brightness = level; System.out.println("  Light brightness: " + level + "%"); }
    public boolean isOn()                 { return on; }
    public int getBrightness()            { return brightness; }
}

// ── Command ───────────────────────────────────────────────────────────────────

interface Command {
    void execute();
    void undo();
}

// ── Concrete Commands (records carry their undo state) ────────────────────────

record TurnOnCommand(SmartLight light) implements Command {
    public void execute() { light.turnOn(); }
    public void undo()    { light.turnOff(); }
}

record TurnOffCommand(SmartLight light) implements Command {
    public void execute() { light.turnOff(); }
    public void undo()    { light.turnOn(); }
}

record SetBrightnessCommand(SmartLight light, int newLevel, int previousLevel) implements Command {
    public void execute() { light.setBrightness(newLevel); }
    public void undo()    { light.setBrightness(previousLevel); }
}

// ── Invoker ───────────────────────────────────────────────────────────────────

class RemoteControl {
    private final Deque<Command> history = new ArrayDeque<>();

    public void press(Command command) {
        command.execute();
        history.push(command);
    }

    public void undoLast() {
        if (!history.isEmpty()) {
            System.out.println("  [Undo]");
            history.pop().undo();
        } else {
            System.out.println("  [Nothing to undo]");
        }
    }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        var light  = new SmartLight();
        var remote = new RemoteControl();

        System.out.println("=== Executing commands ===");
        remote.press(new TurnOnCommand(light));
        remote.press(new SetBrightnessCommand(light, 50, light.getBrightness()));
        remote.press(new SetBrightnessCommand(light, 20, 50));
        remote.press(new TurnOffCommand(light));

        System.out.println("\n=== Undoing commands ===");
        remote.undoLast();
        remote.undoLast();
        remote.undoLast();
        remote.undoLast();
        remote.undoLast();  // nothing left
    }
}
