package designpatterns.structural.proxy;

/**
 * Proxy Pattern
 *
 * Intent: Provide a surrogate or placeholder for another object to control
 * access to it.
 *
 * Three proxy variants shown:
 *  1. Virtual Proxy   — defers expensive creation until first access (lazy loading)
 *  2. Protection Proxy — controls access based on permissions
 */

// ── Subject ───────────────────────────────────────────────────────────────────

interface Image {
    void display();
    String filename();
}

// ── Real Subject (expensive to instantiate) ───────────────────────────────────

class RealImage implements Image {
    private final String filename;

    RealImage(String filename) {
        this.filename = filename;
        loadFromDisk();
    }

    private void loadFromDisk() {
        System.out.println("  [RealImage] Loading from disk: " + filename);
    }

    public void display()    { System.out.println("  [RealImage] Displaying: " + filename); }
    public String filename() { return filename; }
}

// ── 1. Virtual Proxy (lazy loading) ──────────────────────────────────────────

class LazyImage implements Image {
    private final String filename;
    private RealImage realImage;  // null until first access

    LazyImage(String filename) {
        this.filename = filename;
        System.out.println("  [LazyImage] Created proxy for: " + filename + " (not loaded yet)");
    }

    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.display();
    }

    public String filename() { return filename; }
}

// ── 2. Protection Proxy ───────────────────────────────────────────────────────

record User(String name, String role) {}

class ProtectedImage implements Image {
    private final Image   image;
    private final String  requiredRole;
    private final User    currentUser;

    ProtectedImage(Image image, String requiredRole, User currentUser) {
        this.image        = image;
        this.requiredRole = requiredRole;
        this.currentUser  = currentUser;
    }

    public void display() {
        if (currentUser.role().equals(requiredRole)) {
            image.display();
        } else {
            System.out.printf("  [ProtectedImage] Access denied for '%s' (requires '%s')%n",
                    currentUser.name(), requiredRole);
        }
    }

    public String filename() { return image.filename(); }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        // ── Virtual Proxy ──────────────────────────────────────────────────────
        System.out.println("=== Virtual Proxy (Lazy Loading) ===");
        Image img1 = new LazyImage("wallpaper-4k.jpg");
        Image img2 = new LazyImage("profile-photo.png");

        System.out.println("\n[App] User scrolled — displaying img1:");
        img1.display();                // loads + displays
        System.out.println("\n[App] User sees img1 again:");
        img1.display();                // cached — no reload
        System.out.println("\n[App] User reveals img2:");
        img2.display();                // loads + displays

        // ── Protection Proxy ───────────────────────────────────────────────────
        System.out.println("\n=== Protection Proxy ===");
        Image classified = new LazyImage("classified-data.jpg");

        var guest = new User("Alice", "guest");
        var admin = new User("Bob",   "admin");

        new ProtectedImage(classified, "admin", guest).display();
        new ProtectedImage(classified, "admin", admin).display();
    }
}
