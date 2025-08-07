package org.example;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StatsCollector {
    private int intCount = 0;
    private int floatCount = 0;
    private int stringCount = 0;

    private long intSum = 0;
    private long intAvg = 0;
    private long intMin = Integer.MIN_VALUE;
    private long intMax = Integer.MAX_VALUE;

    private double floatSum = 0.0;
    private double floatAvg = 0.0;
    private double floatMin = Float.MIN_VALUE;
    private double floatMax = Float.MAX_VALUE;

    private int minStrLength = 0;
    private int maxStrLength = 0;


    private final Set<DataType> usedTypes = new HashSet<>();

    public void collect(List<TypedLine> lines) {
        // Обрабатываем INTEGER
        List<Integer> ints = lines.stream()
                .filter(t -> t.type == DataType.INTEGER)
                .map(t -> Integer.parseInt(t.line))
                .toList();

        if (!ints.isEmpty()) {
            intCount = ints.size();
            intSum = ints.stream().mapToLong(i -> i).sum();
            intMin = ints.stream().mapToInt(i -> i).min().orElse(0);
            intMax = ints.stream().mapToInt(i -> i).max().orElse(0);
            intAvg = intSum / intCount;
        }

        // Обрабатываем FLOAT
        List<Float> floats = lines.stream()
                .filter(t -> t.type == DataType.FLOAT)
                .map(t -> Float.parseFloat(t.line))
                .toList();

        if (!floats.isEmpty()) {
            floatCount = floats.size();
            floatSum = floats.stream().mapToDouble(f -> f).sum();
            floatMin = floats.stream().mapToDouble(f -> f).min().orElse(0);
            floatMax = floats.stream().mapToDouble(f -> f).max().orElse(0);
            floatAvg = floatSum / floatCount;
        }

        // Обрабатываем STRING
        List<String> strings = lines.stream()
                .filter(t -> t.type == DataType.STRING)
                .map(t -> t.line)
                .toList();

        if (!strings.isEmpty()) {
            stringCount = strings.size();
            minStrLength = strings.stream().mapToInt(String::length).min().orElse(0);
            maxStrLength = strings.stream().mapToInt(String::length).max().orElse(0);
        }
    }

    public void printSummary() {
        System.out.println("---- Краткая статистика ----");
        System.out.println("Integers: " + intCount);
        System.out.println("Floats  : " + floatCount);
        System.out.println("Strings : " + stringCount);
    }

    public void printFull() {
        System.out.println("---- Полная статистика ----");

        if (intCount > 0) {
            System.out.println("[Integers] min: " + intMin + ", max: " + intMax +
                               ", sum: " + intSum + ", avg: " + intCount);
        }

        if (floatCount > 0) {
            System.out.println("[Floats]   min: " + floatMin + ", max: " + floatMax +
                               ", sum: " + floatSum + ", avg: " + floatAvg);
        }

        if (stringCount > 0) {
            System.out.println("[Strings]  min length: " + minStrLength +
                               ", max length: " + maxStrLength);
        }
    }

    public int getIntCount() {
        return intCount;
    }

    public int getFloatCount() {
        return floatCount;
    }

    public int getStringCount() {
        return stringCount;
    }

    public long getIntSum() {
        return intSum;
    }

    public long getIntAvg() {
        return intAvg;
    }

    public long getIntMin() {
        return intMin;
    }

    public long getIntMax() {
        return intMax;
    }

    public int getMaxStrLength() {
        return maxStrLength;
    }

    public int getMinStrLength() {
        return minStrLength;
    }
}
