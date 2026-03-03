package designpatterns.behavioral.memento;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Memento Pattern
 *
 * Intent: Without violating encapsulation, capture and externalise an object's
 * internal state so that the object can be restored to this state later.
 *
 * Example: Text editor with unlimited undo support.
 * Java 25: record as the Memento — naturally immutable, no need for a separate class.
 */

// ── Memento (immutable snapshot) ──────────────────────────────────────────────

record EditorMemento(String content, int cursorPosition) {}

// ── Originator ────────────────────────────────────────────────────────────────

class TextEditor {
    private String content        = "";
    private int    cursorPosition = 0;

    /** Insert text at the current cursor position. */
    public void type(String text) {
        content = content.substring(0, cursorPosition)
                + text
                + content.substring(cursorPosition);
        cursorPosition += text.length();
    }

    /** Delete {@code count} characters to the left of the cursor. */
    public void delete(int count) {
        int start = Math.max(0, cursorPosition - count);
        content       = content.substring(0, start) + content.substring(cursorPosition);
        cursorPosition = start;
    }

    /** Move cursor to absolute position. */
    public void moveCursor(int position) {
        cursorPosition = Math.max(0, Math.min(position, content.length()));
    }

    public EditorMemento save() {
        return new EditorMemento(content, cursorPosition);
    }

    public void restore(EditorMemento m) {
        this.content        = m.content();
        this.cursorPosition = m.cursorPosition();
    }

    @Override
    public String toString() {
        return "\"" + content + "\"  cursor@" + cursorPosition;
    }
}

// ── Caretaker ─────────────────────────────────────────────────────────────────

class UndoHistory {
    private final Deque<EditorMemento> stack = new ArrayDeque<>();

    public void push(TextEditor editor) {
        stack.push(editor.save());
    }

    public void undo(TextEditor editor) {
        if (!stack.isEmpty()) editor.restore(stack.pop());
        else System.out.println("  [Nothing to undo]");
    }

    public int size() { return stack.size(); }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        var editor  = new TextEditor();
        var history = new UndoHistory();

        history.push(editor);       System.out.println("Initial : " + editor);

        editor.type("Hello");
        history.push(editor);       System.out.println("Typed   : " + editor);

        editor.type(", World");
        history.push(editor);       System.out.println("Typed   : " + editor);

        editor.type("!");
        history.push(editor);       System.out.println("Typed   : " + editor);

        editor.moveCursor(5);
        editor.delete(5);           // deletes "Hello"
        System.out.println("Deleted : " + editor);

        System.out.println("\n--- Undo ---");
        history.undo(editor); System.out.println("Undo 1  : " + editor);
        history.undo(editor); System.out.println("Undo 2  : " + editor);
        history.undo(editor); System.out.println("Undo 3  : " + editor);
        history.undo(editor); System.out.println("Undo 4  : " + editor);
        history.undo(editor); // nothing left
    }
}
