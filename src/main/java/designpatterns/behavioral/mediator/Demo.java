package designpatterns.behavioral.mediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Mediator Pattern
 *
 * Intent: Define an object that encapsulates how a set of objects interact.
 * Promotes loose coupling by keeping objects from referring to each other
 * explicitly, and lets you vary their interaction independently.
 *
 * Example: Chat room mediator that routes messages between users, keeping them
 *          decoupled from each other.
 */

// ── Mediator Interface ────────────────────────────────────────────────────────

interface ChatMediator {
    void sendMessage(String message, ChatUser sender);
    void sendPrivateMessage(String message, ChatUser sender, String recipientName);
    void join(ChatUser user);
}

// ── Colleague ─────────────────────────────────────────────────────────────────

abstract class ChatUser {
    protected final ChatMediator mediator;
    protected final String       name;

    ChatUser(ChatMediator mediator, String name) {
        this.mediator = mediator;
        this.name     = name;
    }

    public String name() { return name; }

    abstract void send(String message);
    abstract void sendPrivate(String recipientName, String message);
    abstract void receive(String message, String senderName);
}

// ── Concrete Colleague ────────────────────────────────────────────────────────

class User extends ChatUser {
    User(ChatMediator mediator, String name) {
        super(mediator, name);
    }

    public void send(String message) {
        System.out.println("[" + name + " → all] " + message);
        mediator.sendMessage(message, this);
    }

    public void sendPrivate(String recipientName, String message) {
        System.out.println("[" + name + " → " + recipientName + "] " + message);
        mediator.sendPrivateMessage(message, this, recipientName);
    }

    public void receive(String message, String senderName) {
        System.out.println("  (" + name + " received from " + senderName + "): " + message);
    }
}

// ── Concrete Mediator ─────────────────────────────────────────────────────────

class ChatRoom implements ChatMediator {
    private final List<ChatUser> users = new ArrayList<>();

    public void join(ChatUser user) {
        System.out.println("[ChatRoom] " + user.name() + " joined the room");
        users.add(user);
    }

    public void sendMessage(String message, ChatUser sender) {
        users.stream()
             .filter(u -> u != sender)
             .forEach(u -> u.receive(message, sender.name()));
    }

    public void sendPrivateMessage(String message, ChatUser sender, String recipientName) {
        users.stream()
             .filter(u -> u.name().equals(recipientName))
             .findFirst()
             .ifPresentOrElse(
                 u -> u.receive("[DM] " + message, sender.name()),
                 () -> System.out.println("  [ChatRoom] User '" + recipientName + "' not found")
             );
    }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        var room  = new ChatRoom();
        var alice = new User(room, "Alice");
        var bob   = new User(room, "Bob");
        var carol = new User(room, "Carol");

        room.join(alice);
        room.join(bob);
        room.join(carol);

        System.out.println();
        alice.send("Hello everyone!");

        System.out.println();
        bob.send("Hey Alice, welcome!");

        System.out.println();
        alice.sendPrivate("Bob", "Thanks! Nice to meet you.");
        carol.sendPrivate("Dave", "Are you here?");  // user not found
    }
}
