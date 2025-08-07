import org.example.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WriterManagerTest {
    private final String prefix = "test-output";

    @AfterEach
    public void clean() {
        new File(prefix + "int.txt").delete();
        new File(prefix + "float.txt").delete();
        new File(prefix + "String.txt").delete();
    }

    @Test
    public void testWriterMethodWithAppend() throws IOException, URISyntaxException {
        FileProcessor fileProcessor = new FileProcessor();
        DataClassifier dataClassifier = new DataClassifier();
        WriterManager writerManager = new WriterManager();

        List<String> rawLines = List.of("123", "434", "1.323", "string");
        List<TypedLine> typedLines = new ArrayList<>();

        for (String line : rawLines) {
            DataType type = dataClassifier.classify(line);
            typedLines.add(new TypedLine(type, line));
        }

        writerManager.process("", prefix, typedLines, true);

        // Проверка существования файлов
        assertTrue(new File(prefix + "int.txt").exists());
        assertTrue(new File(prefix + "float.txt").exists());
        assertTrue(new File(prefix + "string.txt").exists());


        List<String> intLines = Files.readAllLines(new File(prefix + "int.txt").toPath());
        List<String> floatLines = Files.readAllLines(new File(prefix + "float.txt").toPath());
        List<String> stringLines = Files.readAllLines(new File(prefix + "string.txt").toPath());

        System.out.println("Int: " + intLines);
        System.out.println("Float: " + floatLines);
        System.out.println("String: " + stringLines);
        System.out.println(new File(prefix + "int.txt").getAbsolutePath());
    }

    @Test
    public void testWriterMethodWithOutAppend() throws IOException, URISyntaxException {
        FileProcessor fileProcessor = new FileProcessor();
        DataClassifier dataClassifier = new DataClassifier();
        WriterManager writerManager = new WriterManager();

        List<String> rawLines = List.of("123", "434", "1.323", "string");
        List<TypedLine> typedLines = new ArrayList<>();

        for (String line : rawLines) {
            DataType type = dataClassifier.classify(line);
            typedLines.add(new TypedLine(type, line));
        }

        writerManager.process("", prefix, typedLines, false);

        // Проверка существования файлов
        assertTrue(new File(prefix + "int.txt").exists());
        assertTrue(new File(prefix + "float.txt").exists());
        assertTrue(new File(prefix + "string.txt").exists());


        List<String> intLines = Files.readAllLines(new File(prefix + "int.txt").toPath());
        List<String> floatLines = Files.readAllLines(new File(prefix + "float.txt").toPath());
        List<String> stringLines = Files.readAllLines(new File(prefix + "string.txt").toPath());

        System.out.println("Int: " + intLines);
        System.out.println("Float: " + floatLines);
        System.out.println("String: " + stringLines);
        System.out.println(new File(prefix + "int.txt").getAbsolutePath());
    }

    @Test
    public void testWriterMethodWithoutFloat() throws IOException, URISyntaxException {
        FileProcessor fileProcessor = new FileProcessor();
        DataClassifier dataClassifier = new DataClassifier();
        WriterManager writerManager = new WriterManager();

        List<String> rawLines = List.of("123", "434", "string");
        List<TypedLine> typedLines = new ArrayList<>();

        for (String line : rawLines) {
            DataType type = dataClassifier.classify(line);
            typedLines.add(new TypedLine(type, line));
        }

        writerManager.process("", prefix, typedLines, true);

        // Проверка существования файлов
        assertTrue(new File(prefix + "int.txt").exists());
        assertFalse(new File(prefix + "float.txt").exists());
        assertTrue(new File(prefix + "string.txt").exists());


        List<String> intLines = Files.readAllLines(new File(prefix + "int.txt").toPath());
        List<String> stringLines = Files.readAllLines(new File(prefix + "string.txt").toPath());

        System.out.println("Int: " + intLines);
        System.out.println("String: " + stringLines);
        System.out.println(new File(prefix + "int.txt").getAbsolutePath());
    }
}
