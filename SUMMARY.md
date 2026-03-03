# Design Patterns — Project Summary

## Package Structure

```
src/main/java/designpatterns/
├── creational/
│   ├── abstractfactory/Demo.java   — Light/Dark UI widget factory
│   ├── builder/Demo.java           — Fluent Pizza builder
│   ├── factorymethod/Demo.java     — PDF/Word/HTML document editors
│   ├── prototype/Demo.java         — Shape registry with cloning
│   └── singleton/Demo.java         — Enum singleton + double-checked locking
├── structural/
│   ├── adapter/Demo.java           — XML→JSON adapter
│   ├── bridge/Demo.java            — Shape × Renderer (vector/raster)
│   ├── composite/Demo.java         — File system tree (File + Directory)
│   ├── decorator/Demo.java         — Coffee shop with stackable extras
│   ├── facade/Demo.java            — Home theater simplified interface
│   ├── flyweight/Demo.java         — Text editor character styles
│   └── proxy/Demo.java             — Lazy-loading + access-control proxies
└── behavioral/
    ├── chainofresponsibility/       — Log level dispatcher chain
    ├── command/                     — Smart-light remote with undo
    ├── interpreter/                 — Arithmetic expression AST
    ├── iterator/                    — Sorted collection with ascending/descending/range iterators
    ├── mediator/                    — Chat room with DM support
    ├── memento/                     — Text editor with unlimited undo
    ├── observer/                    — Stock market with multiple subscriber types
    ├── state/                       — Traffic light state machine
    ├── strategy/                    — Bubble/Selection/Quick Sort + lambda
    ├── templatemethod/              — CSV and JSON data mining pipeline
    └── visitor/                     — Shape area, perimeter, SVG visitors
```

## Java 25 Features Highlighted

| Feature | Used in |
|---|---|
| `record` | Builder (Pizza), Command, Memento, Observer event, Flyweight, Proxy, Chain of Responsibility |
| `sealed interface` | Factory Method, Prototype, Composite, State, Interpreter, Visitor, Chain of Responsibility |
| Pattern matching `switch` | Prototype, State, Visitor, Interpreter printer |
| `@FunctionalInterface` | Strategy (enables lambda strategies) |
| Text blocks | Builder, Adapter |
| `var` | Throughout all demos |

## Build & Run

```bash
# Compile all patterns
./mvnw compile

# Run individual demos
java -cp target/classes designpatterns.creational.abstractfactory.Demo
java -cp target/classes designpatterns.creational.builder.Demo
java -cp target/classes designpatterns.creational.factorymethod.Demo
java -cp target/classes designpatterns.creational.prototype.Demo
java -cp target/classes designpatterns.creational.singleton.Demo

java -cp target/classes designpatterns.structural.adapter.Demo
java -cp target/classes designpatterns.structural.bridge.Demo
java -cp target/classes designpatterns.structural.composite.Demo
java -cp target/classes designpatterns.structural.decorator.Demo
java -cp target/classes designpatterns.structural.facade.Demo
java -cp target/classes designpatterns.structural.flyweight.Demo
java -cp target/classes designpatterns.structural.proxy.Demo

java -cp target/classes designpatterns.behavioral.chainofresponsibility.Demo
java -cp target/classes designpatterns.behavioral.command.Demo
java -cp target/classes designpatterns.behavioral.interpreter.Demo
java -cp target/classes designpatterns.behavioral.iterator.Demo
java -cp target/classes designpatterns.behavioral.mediator.Demo
java -cp target/classes designpatterns.behavioral.memento.Demo
java -cp target/classes designpatterns.behavioral.observer.Demo
java -cp target/classes designpatterns.behavioral.state.Demo
java -cp target/classes designpatterns.behavioral.strategy.Demo
java -cp target/classes designpatterns.behavioral.templatemethod.Demo
java -cp target/classes designpatterns.behavioral.visitor.Demo
```
