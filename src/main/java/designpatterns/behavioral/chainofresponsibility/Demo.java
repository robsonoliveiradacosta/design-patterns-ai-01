package designpatterns.behavioral.chainofresponsibility;

/**
 * Chain of Responsibility Pattern
 *
 * Intent: Avoid coupling the sender of a request to its receiver by giving more
 * than one object a chance to handle the request. Chain the receiving objects
 * and pass the request along the chain until an object handles it.
 *
 * Example: Log message dispatcher — each handler processes only its own level
 *          and passes unknown levels to the next handler in the chain.
 * Java 25: sealed interface with nested records for LogLevel + pattern matching.
 */

// ── Request ───────────────────────────────────────────────────────────────────

sealed interface LogLevel permits LogLevel.Debug, LogLevel.Info, LogLevel.Warning, LogLevel.Error {
    record Debug()   implements LogLevel {}
    record Info()    implements LogLevel {}
    record Warning() implements LogLevel {}
    record Error()   implements LogLevel {}
}

record LogRequest(LogLevel level, String message) {}

// ── Abstract Handler ──────────────────────────────────────────────────────────

abstract class LogHandler {
    private LogHandler next;

    public LogHandler setNext(LogHandler next) {
        this.next = next;
        return next;   // fluent chaining
    }

    public abstract void handle(LogRequest request);

    protected void passToNext(LogRequest request) {
        if (next != null) next.handle(request);
        else System.out.println("[UNHANDLED] " + request.message());
    }
}

// ── Concrete Handlers ─────────────────────────────────────────────────────────

class DebugHandler extends LogHandler {
    public void handle(LogRequest r) {
        if (r.level() instanceof LogLevel.Debug)
            System.out.println("[DEBUG]   " + r.message());
        else
            passToNext(r);
    }
}

class InfoHandler extends LogHandler {
    public void handle(LogRequest r) {
        if (r.level() instanceof LogLevel.Info)
            System.out.println("[INFO]    " + r.message());
        else
            passToNext(r);
    }
}

class WarningHandler extends LogHandler {
    public void handle(LogRequest r) {
        if (r.level() instanceof LogLevel.Warning)
            System.out.println("[WARNING] " + r.message());
        else
            passToNext(r);
    }
}

class ErrorHandler extends LogHandler {
    public void handle(LogRequest r) {
        if (r.level() instanceof LogLevel.Error)
            System.out.println("[ERROR]   " + r.message());
        else
            passToNext(r);
    }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        // Build the chain: DEBUG → INFO → WARNING → ERROR
        var debug = new DebugHandler();
        debug.setNext(new InfoHandler())
             .setNext(new WarningHandler())
             .setNext(new ErrorHandler());

        var requests = new LogRequest[]{
            new LogRequest(new LogLevel.Debug(),   "x = 42"),
            new LogRequest(new LogLevel.Info(),    "Server started on port 8080"),
            new LogRequest(new LogLevel.Warning(), "Memory usage at 80%"),
            new LogRequest(new LogLevel.Error(),   "Database connection failed!"),
        };

        for (var req : requests) {
            debug.handle(req);
        }
    }
}
