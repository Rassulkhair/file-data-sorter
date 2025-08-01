import org.example.FileProcessor;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class FileProcessorTest {

    @Test
    public void TestReadingMultipleFiles() throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file1 = new File(classLoader.getResource("test1.txt").toURI());
        File file2 = new File(classLoader.getResource("test2.txt").toURI());

        System.out.println("Using file1: " + file1.getAbsolutePath());
        System.out.println("Using file2: " + file2.getAbsolutePath());

        FileProcessor fileProcessor = new FileProcessor(List.of(file1, file2));

        List<String> actual = new ArrayList<>();

        // 👇 Вот здесь добавляем логирование
        while (fileProcessor.hasNextLine()) {
            String line = fileProcessor.nextLine();
            System.out.println("LINE: '" + line + "'"); // 👈 ЭТО и добавляем
            actual.add(line);
        }

        List<String> expected = List.of("Hello", "123", "Test", "456", "World", "789");

        assertEquals(expected, actual);

    }
}
