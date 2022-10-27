import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void createFolderTest() {
       String folderPath = "forTest";

       String expected = LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "        Дериктория  " + "\"" + folderPath + "\"" + " успешно создана\n";
       String actual = Main.createFolder(folderPath);

       assertEquals(expected, actual);

    }

    @Test
    void createFileTest() {
        String filePath = "forTest/test.txt";

        String expected = LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "        Файл        " + "\"" + filePath + "\"" + " успешно создан\n";
        String actual = Main.createFile(filePath);

        assertEquals(expected,actual);
    }

    @Test
    void zipFilesTest() {
        File dir = new File("forTest");

        File[] fileList = null;
        if (dir.isDirectory()) {
            fileList = dir.listFiles();
        }
        boolean expected = true;
        boolean actual = Main.zipFiles("./forTest//save.zip", fileList);

        assertEquals(expected, actual);


    }
}