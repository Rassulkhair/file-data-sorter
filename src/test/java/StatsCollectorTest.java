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

        List<String> lines = List.of("123", "434", "1.323", "string");

        List<TypedLine> typedLines = new ArrayList<>();
        for (String line : lines) {
            DataType type = classifier.classify(line);
            typedLines.add(new TypedLine(type, line));
        }

        collector.collect(typedLines);

        assertEquals(2, collector.getIntCount(), "Ожидается 3 целых числа");
        assertEquals(1, collector.getStringCount(), "Ожидается 3 строки");
        assertEquals(1, collector.getFloatCount(), "Ожидается 0 вещественных чисел");

        assertEquals(123 + 434, collector.getIntSum());
        assertEquals(123, collector.getIntMin());
        assertEquals(434, collector.getIntMax());
        assertEquals((123 + 434) / 2, collector.getIntAvg());

        assertEquals(6, collector.getMaxStrLength());
        assertEquals(6, collector.getMinStrLength());

        collector.printSummary();
        collector.printFull();
    }
}
