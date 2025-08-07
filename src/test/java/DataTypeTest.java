import org.example.DataClassifier;
import org.example.DataType;
import org.example.FileProcessor;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataTypeTest {
    @Test
    public void TestDataClassify() throws URISyntaxException {
        DataClassifier dataClassifier = new DataClassifier();
        List<String> actual = List.of("123", "434", "1,323", "string");

        List<String> integers = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        List<String> floats = new ArrayList<>();

        for (var i : actual) {
            DataType dataType = dataClassifier.classify(i);
            if (DataType.FLOAT == dataType) {
                floats.add(i);
            }
            if (DataType.INTEGER == dataType) {
                integers.add(i);
            }
            if (DataType.STRING == dataType) {
                strings.add(i);
            }
        }

        assertEquals(2, integers.size());
        assertEquals(1, floats.size());
        assertEquals(1, strings.size());
    }
}
