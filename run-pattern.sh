#!/usr/bin/env bash
set -euo pipefail

# ── Pattern registry ──────────────────────────────────────────────────────────
declare -A PATTERNS=(
    # Creational
    [abstractfactory]="designpatterns.creational.abstractfactory.Demo"
    [builder]="designpatterns.creational.builder.Demo"
    [factorymethod]="designpatterns.creational.factorymethod.Demo"
    [prototype]="designpatterns.creational.prototype.Demo"
    [singleton]="designpatterns.creational.singleton.Demo"
    # Structural
    [adapter]="designpatterns.structural.adapter.Demo"
    [bridge]="designpatterns.structural.bridge.Demo"
    [composite]="designpatterns.structural.composite.Demo"
    [decorator]="designpatterns.structural.decorator.Demo"
    [facade]="designpatterns.structural.facade.Demo"
    [flyweight]="designpatterns.structural.flyweight.Demo"
    [proxy]="designpatterns.structural.proxy.Demo"
    # Behavioral
    [chainofresponsibility]="designpatterns.behavioral.chainofresponsibility.Demo"
    [command]="designpatterns.behavioral.command.Demo"
    [interpreter]="designpatterns.behavioral.interpreter.Demo"
    [iterator]="designpatterns.behavioral.iterator.Demo"
    [mediator]="designpatterns.behavioral.mediator.Demo"
    [memento]="designpatterns.behavioral.memento.Demo"
    [observer]="designpatterns.behavioral.observer.Demo"
    [state]="designpatterns.behavioral.state.Demo"
    [strategy]="designpatterns.behavioral.strategy.Demo"
    [templatemethod]="designpatterns.behavioral.templatemethod.Demo"
    [visitor]="designpatterns.behavioral.visitor.Demo"
)

# ── Usage ─────────────────────────────────────────────────────────────────────
usage() {
    echo "Usage: $0 <pattern-name>"
    echo ""
    echo "Available patterns:"
    echo ""
    echo "  Creational:"
    echo "    abstractfactory  builder  factorymethod  prototype  singleton"
    echo ""
    echo "  Structural:"
    echo "    adapter  bridge  composite  decorator  facade  flyweight  proxy"
    echo ""
    echo "  Behavioral:"
    echo "    chainofresponsibility  command  interpreter  iterator  mediator"
    echo "    memento  observer  state  strategy  templatemethod  visitor"
    echo ""
    echo "Example:"
    echo "  $0 builder"
    exit 1
}

# ── Validate input ────────────────────────────────────────────────────────────
if [[ $# -lt 1 ]]; then
    usage
fi

PATTERN=$(echo "$1" | tr '[:upper:]' '[:lower:]')

if [[ -z "${PATTERNS[$PATTERN]+_}" ]]; then
    echo "Error: unknown pattern '$PATTERN'"
    echo ""
    usage
fi

# ── Compile if needed ─────────────────────────────────────────────────────────
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

if [[ ! -d target/classes ]]; then
    echo "Compiling project..."
    ./mvnw compile -q
fi

# ── Run ───────────────────────────────────────────────────────────────────────
CLASS="${PATTERNS[$PATTERN]}"
echo "Running: $CLASS"
echo "$(printf '─%.0s' {1..50})"
java -cp target/classes "$CLASS"
