import org.example.DataClassifier;
import org.example.DataType;
import org.example.FileProcessor;
import org.example.WriterManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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
    public void testWriterMethod() throws IOException, URISyntaxException {
        FileProcessor fileProcessor = new FileProcessor();
        DataClassifier dataClassifier = new DataClassifier();
        WriterManager writerManager = new WriterManager(prefix);

        List<String> files = List.of("test1.txt", "test2.txt");
        List<String> lines = fileProcessor.processFile(files);

        for (String line : lines) {
            DataType dataType = dataClassifier.classify(line);
            writerManager.write(dataType, line);
        }

        writerManager.close();

        //Делаем проверку создались ли файлы
        assertTrue(new File(prefix + "int.txt").exists());
        assertTrue(new File(prefix + "float.txt").exists());
        assertTrue(new File(prefix + "string.txt").exists());


        //Делаем проверку на содержимое файлов
        List<String> intLines = Files.readAllLines(new File(prefix + "int.txt").toPath());
        List<String> floatLines = Files.readAllLines(new File(prefix + "float.txt").toPath());
        List<String> stringLines = Files.readAllLines(new File(prefix + "string.txt").toPath());

        System.out.println("Int: " + intLines);
        System.out.println("Float: " + floatLines);
        System.out.println("String: " + stringLines);
        System.out.println(new File(prefix + "int.txt").getAbsolutePath());


    }
}
