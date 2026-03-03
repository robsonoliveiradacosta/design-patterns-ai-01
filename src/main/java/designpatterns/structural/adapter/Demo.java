package designpatterns.structural.adapter;

/**
 * Adapter Pattern
 *
 * Intent: Convert the interface of a class into another interface that clients
 * expect. Lets classes work together that couldn't otherwise because of
 * incompatible interfaces.
 *
 * Example: DataProcessor expects a JsonParser, but only an XmlParser exists.
 *          XmlToJsonAdapter bridges the gap.
 */

// ── Target interface (what the client expects) ────────────────────────────────

interface JsonParser {
    String parse(String data);
}

// ── Adaptee (existing class with incompatible interface) ──────────────────────

class XmlParser {
    public String parseXml(String xml) {
        // Simulates stripping tags and returning raw content
        var content = xml.replaceAll("<[^>]+>", "").trim();
        return "XML_PARSED[" + content + "]";
    }
}

// ── Adapter ───────────────────────────────────────────────────────────────────

class XmlToJsonAdapter implements JsonParser {
    private final XmlParser xmlParser;

    XmlToJsonAdapter(XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    @Override
    public String parse(String data) {
        var parsed = xmlParser.parseXml(data);
        return """
               { "data": "%s" }""".formatted(parsed);
    }
}

// ── Client (only knows JsonParser) ────────────────────────────────────────────

class DataProcessor {
    private final JsonParser parser;

    DataProcessor(JsonParser parser) {
        this.parser = parser;
    }

    void process(String raw) {
        System.out.println("Result: " + parser.parse(raw));
    }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        System.out.println("=== Native JSON Parser ===");
        // Lambda satisfies the functional interface
        JsonParser nativeJson = data -> "{ \"value\": \"" + data + "\" }";
        new DataProcessor(nativeJson).process("hello world");

        System.out.println("\n=== XML via Adapter ===");
        var adapter = new XmlToJsonAdapter(new XmlParser());
        new DataProcessor(adapter).process("<name>John Doe</name>");
        new DataProcessor(adapter).process("<city>São Paulo</city>");
    }
}
