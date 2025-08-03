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
        FileProcessor fileProcessor = new FileProcessor();
        List<String> fileUrls = List.of("test1.txt" , "test2.txt");
        List<String> actual = fileProcessor.processFile(fileUrls);


        for (var i : actual){
            System.out.println(i);
        }
        assertEquals(12, actual.size());
    }
}
