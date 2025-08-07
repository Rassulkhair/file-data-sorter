import org.example.*;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatsCollectorTest {
    @Test
    public void testStatsFromFiles() throws URISyntaxException {
        FileProcessor processor = new FileProcessor();
        DataClassifier classifier = new DataClassifier();
        StatsCollector collector = new StatsCollector();

        List<String> fileNames = List.of("test4.txt", "test5.txt");
        List<String> lines = processor.processFile(fileNames);

        List<TypedLine> typedLines = new ArrayList<>();
        for (String line : lines) {
            DataType type = classifier.classify(line);
            typedLines.add(new TypedLine(type, line));
        }

        collector.collect(typedLines);

        assertEquals(3, collector.getIntCount(), "Ожидается 3 целых числа");
        assertEquals(3, collector.getStringCount(), "Ожидается 3 строки");
        assertEquals(0, collector.getFloatCount(), "Ожидается 0 вещественных чисел");

        assertEquals(123 + 456 + 789, collector.getIntSum());
        assertEquals(123, collector.getIntMin());
        assertEquals(789, collector.getIntMax());
        assertEquals((123 + 456 + 789) / 3, collector.getIntAvg());

        assertEquals(5, collector.getMaxStrLength());
        assertEquals(4, collector.getMinStrLength());

        collector.printSummary();
        collector.printFull();
    }
}
