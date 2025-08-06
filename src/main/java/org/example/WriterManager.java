package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.example.DataType.*;

public class WriterManager {
    private BufferedWriter intWriter;
    private BufferedWriter floatWriter;
    private BufferedWriter stringWriter;

    public WriterManager() {}

    public void process(String prefix, List<TypedLine> lines) throws IOException {
        // создать файлы
        intWriter = new BufferedWriter(new FileWriter(prefix + "int.txt"));
        floatWriter = new BufferedWriter(new FileWriter(prefix + "float.txt"));
        stringWriter = new BufferedWriter(new FileWriter(prefix + "string.txt"));

        // распределить строки
        for (TypedLine line : lines) {
            switch (line.type) {
                case INTEGER -> intWriter.write(line.line + System.lineSeparator());
                case FLOAT   -> floatWriter.write(line.line + System.lineSeparator());
                case STRING  -> stringWriter.write(line.line + System.lineSeparator());
            }
        }

        // закрыть потоки
        close();
    }

    private void close() throws IOException {
        intWriter.close();
        floatWriter.close();
        stringWriter.close();
    }
}
