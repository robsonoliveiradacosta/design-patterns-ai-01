package designpatterns.structural.facade;

/**
 * Facade Pattern
 *
 * Intent: Provide a unified, simplified interface to a set of interfaces in a
 * subsystem. Makes the subsystem easier to use.
 *
 * Example: HomeTheaterFacade simplifies controlling a home theater system that
 *          consists of Amplifier, DvdPlayer, Projector, and Lights.
 */

// ── Subsystems ────────────────────────────────────────────────────────────────

class Amplifier {
    void on()                   { System.out.println("  Amplifier: ON"); }
    void off()                  { System.out.println("  Amplifier: OFF"); }
    void setVolume(int level)   { System.out.println("  Amplifier: volume → " + level); }
    void setSurroundSound()     { System.out.println("  Amplifier: surround sound ON"); }
}

class DvdPlayer {
    void on()                   { System.out.println("  DVD Player: ON"); }
    void off()                  { System.out.println("  DVD Player: OFF"); }
    void play(String title)     { System.out.println("  DVD Player: playing '" + title + "'"); }
    void stop()                 { System.out.println("  DVD Player: stopped"); }
}

class Projector {
    void on()                   { System.out.println("  Projector: ON"); }
    void off()                  { System.out.println("  Projector: OFF"); }
    void setWideScreenMode()    { System.out.println("  Projector: widescreen mode ON"); }
    void setInput(String src)   { System.out.println("  Projector: input → " + src); }
}

class Lights {
    void dim(int level)         { System.out.println("  Lights: dimmed to " + level + "%"); }
    void on()                   { System.out.println("  Lights: ON (full brightness)"); }
}

class PopcornMaker {
    void on()                   { System.out.println("  Popcorn Maker: ON"); }
    void off()                  { System.out.println("  Popcorn Maker: OFF"); }
    void pop()                  { System.out.println("  Popcorn Maker: popping!"); }
}

// ── Facade ────────────────────────────────────────────────────────────────────

class HomeTheaterFacade {
    private final Amplifier    amp;
    private final DvdPlayer    dvd;
    private final Projector    projector;
    private final Lights       lights;
    private final PopcornMaker popcorn;

    HomeTheaterFacade(Amplifier amp, DvdPlayer dvd,
                      Projector projector, Lights lights, PopcornMaker popcorn) {
        this.amp = amp; this.dvd = dvd;
        this.projector = projector; this.lights = lights; this.popcorn = popcorn;
    }

    void watchMovie(String title) {
        System.out.println("▶ Getting ready to watch: " + title);
        popcorn.on();
        popcorn.pop();
        lights.dim(10);
        projector.on();
        projector.setWideScreenMode();
        projector.setInput("DVD");
        amp.on();
        amp.setSurroundSound();
        amp.setVolume(8);
        dvd.on();
        dvd.play(title);
    }

    void endMovie() {
        System.out.println("\n■ Shutting down home theater");
        dvd.stop();
        dvd.off();
        amp.off();
        projector.off();
        popcorn.off();
        lights.on();
    }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        var theater = new HomeTheaterFacade(
                new Amplifier(),
                new DvdPlayer(),
                new Projector(),
                new Lights(),
                new PopcornMaker()
        );

        theater.watchMovie("Inception");
        theater.endMovie();
    }
}
