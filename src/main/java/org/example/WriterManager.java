package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriterManager {
    private final BufferedWriter intWriter;
    private final BufferedWriter floatWriter;
    private final BufferedWriter stringWriter;


    // Конструктор создаёт 3 файла с заданным префиксом (int, float, string)
    // и открывает потоки для записи строк по типам данных
    public WriterManager(String prefix) throws IOException {
        intWriter = new BufferedWriter(new FileWriter(prefix + "int.txt"));
        floatWriter = new BufferedWriter(new FileWriter(prefix + "float.txt"));
        stringWriter = new BufferedWriter(new FileWriter(prefix + "string.txt"));
    }

    public void write(DataType dataType, String line) throws IOException {
        switch (dataType) {
            case INTEGER -> intWriter.write(line + System.lineSeparator());
            case FLOAT -> floatWriter.write(line + System.lineSeparator());
            case STRING -> stringWriter.write(line + System.lineSeparator());
        }
    }


    public void close() throws IOException {
        intWriter.close();
        floatWriter.close();
        stringWriter.close();
    }
}
