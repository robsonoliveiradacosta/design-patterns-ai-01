package designpatterns.behavioral.templatemethod;

import java.util.List;

/**
 * Template Method Pattern
 *
 * Intent: Define the skeleton of an algorithm in an operation, deferring some
 * steps to subclasses. Template Method lets subclasses redefine certain steps
 * of an algorithm without changing the algorithm's structure.
 *
 * Example: Data mining pipeline where extraction and parsing differ by format
 *          (CSV, JSON) but analysis and reporting are shared.
 */

// ── Abstract Class with Template Method ───────────────────────────────────────

abstract class DataMiner {

    // Template method — defines the algorithm skeleton (final = not overridable)
    public final void mine(String source) {
        System.out.println("=== Mining: " + source + " ===");
        var raw     = extractData(source);
        var records = parseData(raw);
        var result  = analyzeData(records);    // hook: has a default implementation
        generateReport(source, result);
        System.out.println();
    }

    // Steps that must be implemented by subclasses
    protected abstract String       extractData(String source);
    protected abstract List<Double> parseData(String rawData);

    // Hook — subclasses may override for custom analysis
    protected List<Double> analyzeData(List<Double> data) {
        var avg = data.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        var max = data.stream().mapToDouble(Double::doubleValue).max().orElse(0);
        var min = data.stream().mapToDouble(Double::doubleValue).min().orElse(0);
        System.out.printf("  Analysis → count=%d, avg=%.2f, min=%.2f, max=%.2f%n",
                data.size(), avg, min, max);
        return data;
    }

    // Common step shared by all subclasses
    private void generateReport(String source, List<Double> data) {
        System.out.println("  Report generated for '" + source + "' with " + data.size() + " records");
    }
}

// ── Concrete Subclasses ───────────────────────────────────────────────────────

class CsvDataMiner extends DataMiner {

    @Override
    protected String extractData(String source) {
        System.out.println("  [CSV] Reading file: " + source);
        return "1.5,2.3,4.1,3.8,5.0,2.9";   // simulated CSV content
    }

    @Override
    protected List<Double> parseData(String raw) {
        System.out.println("  [CSV] Parsing comma-separated values...");
        return java.util.Arrays.stream(raw.split(","))
                .map(Double::parseDouble)
                .toList();
    }
}

class JsonDataMiner extends DataMiner {

    @Override
    protected String extractData(String source) {
        System.out.println("  [JSON] Fetching from: " + source);
        return "[10.0, 20.5, 15.3, 8.7, 12.0]";   // simulated JSON array
    }

    @Override
    protected List<Double> parseData(String raw) {
        System.out.println("  [JSON] Parsing JSON array...");
        // Simulated parsing: strip brackets and split by comma
        var cleaned = raw.replaceAll("[\\[\\]\\s]", "");
        return java.util.Arrays.stream(cleaned.split(","))
                .map(Double::parseDouble)
                .toList();
    }

    // Override hook to add extra statistics
    @Override
    protected List<Double> analyzeData(List<Double> data) {
        super.analyzeData(data);
        var sum = data.stream().mapToDouble(Double::doubleValue).sum();
        System.out.printf("  [JSON] Extra → sum=%.2f%n", sum);
        return data;
    }
}

// ── Demo ──────────────────────────────────────────────────────────────────────

public class Demo {
    public static void main(String[] args) {
        new CsvDataMiner().mine("sales-2024.csv");
        new JsonDataMiner().mine("https://api.example.com/metrics");
    }
}
