package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.example.DataType.*;

public class WriterManager {
    private BufferedWriter intWriter;
    private BufferedWriter floatWriter;
    private BufferedWriter stringWriter;

    public WriterManager() {}

    public void process(String path, String prefix, List<TypedLine> lines, boolean append) throws IOException {
        String separator = path.endsWith("/") ? "" : "/";
        String intPath = path.isEmpty() ? prefix + "int.txt" : path + separator + prefix + "int.txt";
        String flPath = path.isEmpty() ? prefix + "float.txt" : path + separator + prefix + "float.txt";
        String strPath = path.isEmpty() ? prefix + "string.txt" : path + separator + prefix + "string.txt";

        boolean canAppend = append &&
                            new File(intPath).exists() &&
                            new File(strPath).exists() &&
                            new File(flPath).exists();

        boolean hasInt = lines.stream().anyMatch(line -> line.type == DataType.INTEGER);
        boolean hasFloat = lines.stream().anyMatch(line -> line.type == DataType.FLOAT);
        boolean hasString = lines.stream().anyMatch(line -> line.type == DataType.STRING);


        if (hasInt) {
            intWriter = new BufferedWriter(new FileWriter(intPath, canAppend));
        }
        if (hasFloat) {
            floatWriter = new BufferedWriter(new FileWriter(flPath, canAppend));
        }
        if (hasString) {
            stringWriter = new BufferedWriter(new FileWriter(strPath, canAppend));
        }

        // распределить строки
        for (TypedLine line : lines) {
            switch (line.type) {
                case INTEGER -> intWriter.write(line.line + System.lineSeparator());
                case FLOAT   -> floatWriter.write(line.line + System.lineSeparator());
                case STRING  -> stringWriter.write(line.line + System.lineSeparator());
            }
        }

        // закрыть потоки
        if (intWriter != null) intWriter.close();
        if (floatWriter != null) floatWriter.close();
        if (stringWriter != null) stringWriter.close();
    }
}
