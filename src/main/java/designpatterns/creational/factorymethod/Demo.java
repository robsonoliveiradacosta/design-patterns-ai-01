package designpatterns.creational.factorymethod;

/**
 * Factory Method Pattern
 *
 * Intent: Define an interface for creating an object, but let subclasses decide
 * which class to instantiate. Lets a class defer instantiation to subclasses.
 *
 * Example: Document editor where each editor type produces its own document format.
 * Java 25: sealed interface for the product hierarchy + pattern matching.
 */

// ── Product (sealed interface) ────────────────────────────────────────────────

sealed interface Document permits PdfDocument, WordDocument, HtmlDocument {
    void open();
    void save(String filename);
    String format();
}

record PdfDocument(String content) implements Document {
    public void open()              { System.out.println("Opening PDF viewer"); }
    public void save(String name)   { System.out.println("Saving PDF: " + name + ".pdf"); }
    public String format()          { return "PDF"; }
}

record WordDocument(String content) implements Document {
    public void open()              { System.out.println("Opening Word processor"); }
    public void save(String name)   { System.out.println("Saving Word: " + name + ".docx"); }
    public String format()          { return "DOCX"; }
}

record HtmlDocument(String content) implements Document {
    public void open()              { System.out.println("Opening browser"); }
    public void save(String name)   { System.out.println("Saving HTML: " + name + ".html"); }
    public String format()          { return "HTML"; }
}

// ── Creator (abstract) ────────────────────────────────────────────────────────

abstract class DocumentEditor {

    // Factory Method — subclasses override this
    abstract Document createDocument(String content);

    // Template method that uses the factory method
    public final void newDocument(String content, String filename) {
        var doc = createDocument(content);
        System.out.printf("[%s Editor] ", doc.format());
        doc.open();
        doc.save(filename);
        System.out.println();
    }
}

// ── Concrete Creators ─────────────────────────────────────────────────────────

class PdfEditor extends DocumentEditor {
    public Document createDocument(String content) { return new PdfDocument(content); }
}

class WordEditor extends DocumentEditor {
    public Document createDocument(String content) { return new WordDocument(content); }
}

class HtmlEditor extends DocumentEditor {
    public Document createDocument(String content) { return new HtmlDocument(content); }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        var editors = new DocumentEditor[]{ new PdfEditor(), new WordEditor(), new HtmlEditor() };
        var names   = new String[]{ "annual-report", "meeting-notes", "landing-page" };

        for (int i = 0; i < editors.length; i++) {
            editors[i].newDocument("Hello, World!", names[i]);
        }
    }
}
