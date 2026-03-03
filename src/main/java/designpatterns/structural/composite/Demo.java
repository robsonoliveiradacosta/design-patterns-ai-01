package designpatterns.structural.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite Pattern
 *
 * Intent: Compose objects into tree structures to represent part-whole hierarchies.
 * Lets clients treat individual objects and compositions uniformly.
 *
 * Example: File system where both File (leaf) and Directory (composite) share
 *          the same FileSystemItem interface.
 * Java 25: sealed interface for the component hierarchy, record for the leaf.
 */

// ── Component (sealed interface) ──────────────────────────────────────────────

sealed interface FileSystemItem permits File, Directory {
    String name();
    long sizeInBytes();
    void print(String indent);
}

// ── Leaf ──────────────────────────────────────────────────────────────────────

record File(String name, long sizeInBytes) implements FileSystemItem {
    public void print(String indent) {
        System.out.printf("%s  %s  (%,d bytes)%n", indent, name, sizeInBytes);
    }
}

// ── Composite ─────────────────────────────────────────────────────────────────

non-sealed class Directory implements FileSystemItem {
    private final String name;
    private final List<FileSystemItem> children = new ArrayList<>();

    Directory(String name) { this.name = name; }

    public String name() { return name; }

    public void add(FileSystemItem item) { children.add(item); }

    public long sizeInBytes() {
        return children.stream().mapToLong(FileSystemItem::sizeInBytes).sum();
    }

    public void print(String indent) {
        System.out.printf("%s [%s]  (%,d bytes total)%n", indent, name, sizeInBytes());
        children.forEach(child -> child.print(indent + "  "));
    }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        var root = new Directory("root");

        var documents = new Directory("documents");
        documents.add(new File("resume.pdf",       102_400));
        documents.add(new File("cover-letter.docx", 20_480));

        var photos = new Directory("photos");
        photos.add(new File("profile.png",    512_000));
        photos.add(new File("vacation.jpg", 3_145_728));

        var album2024 = new Directory("2024");
        album2024.add(new File("birthday.jpg", 2_097_152));
        album2024.add(new File("trip.jpg",     1_572_864));
        photos.add(album2024);

        root.add(documents);
        root.add(photos);
        root.add(new File("readme.txt", 1_024));

        root.print("");

        System.out.printf("%nTotal size: %,d bytes%n", root.sizeInBytes());
    }
}
