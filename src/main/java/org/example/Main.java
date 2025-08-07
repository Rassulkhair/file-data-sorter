package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        if (args.length == 0) {
            System.out.println("аргументы не указаны, используйте к примеру:"
                               + " java -jar target/util-1.jar -s -a -p prefix- in1.txt in2.txt");
            return;
        }

        boolean append = false;
        boolean fullStats = false;
        String prefix = "";
        String outputPath = "";

        List<String> files = new ArrayList<>();

        // Разбор аргументов
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-a" -> append = true;
                case "-s" -> fullStats = false;
                case "-f" -> fullStats = true;
                case "-p" -> {
                    if (i + 1 < args.length) {
                        prefix = args[++i];
                    } else {
                        System.out.println("Ошибка: Пропущен префикс после -p");
                        return;
                    }
                }
                case "-o" -> {
                    if (i + 1 < args.length) {
                        outputPath = args[++i];
                    } else {
                        System.out.println("Ошибка: Пропущен путь после -o");
                        return;
                    }
                }
                default -> {
                    if (!args[i].startsWith("-")) {
                        files.add(args[i]);
                    }
                }
            }
        }

        if (files.isEmpty()) {
            System.out.println("Ошибка: Не указаны входные файлы");
            return;
        }

        // Шаг 1: Чтение файлов
        FileProcessor fileProcessor = new FileProcessor();
        List<String> rawLines = fileProcessor.processFile(files);

        // Шаг 2: Классификация
        DataClassifier classifier = new DataClassifier();
        List<TypedLine> typedLines = new ArrayList<>();
        for (String line : rawLines) {
            DataType type = classifier.classify(line);
            typedLines.add(new TypedLine(type, line));
        }

        // Шаг 3: Запись в выходные файлы
        WriterManager writer = new WriterManager();
        writer.process(outputPath, prefix, typedLines, append);

        // Шаг 4: Сбор статистики
        StatsCollector stats = new StatsCollector();
        stats.collect(typedLines);
        if (fullStats) {
            stats.printFull();
        } else {
            stats.printSummary();
        }
    }
}