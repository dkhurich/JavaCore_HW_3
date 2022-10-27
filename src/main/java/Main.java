import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static final String MAIN_PATH = "D:\\TestFolder\\Games\\";

    public static String createFolder(String folderPath) {
        File newFolder = new File(folderPath);
        if (newFolder.mkdir()) {
            return LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "        Дериктория  " + "\"" + folderPath + "\"" + " успешно создана\n";
        } else {
            return LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "     Дериктория " + "\"" + folderPath + "\"" + " НЕ СОЗДАНА! ПРОВЕРЬТЕ ПРАВА НА СОЗДАНИЕ ПАПОК ИЛИ КОРРЕКТНОСТЬ УКАЗАННОГО ПУТИ\n";
        }
    }

    public static String createFile(String filePath) {
        File newFile = new File(filePath);
        try {
            if (newFile.createNewFile()) {
                return LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "        Файл        " + "\"" + filePath + "\"" + " успешно создан\n";
            } else {
                return LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "     Файл       " + "\"" + filePath + "\"" + " НЕ СОЗДАН! ПРОВЕРЬТЕ ПРАВА НА СОЗДАНИЕ ФАЙЛОВ ИЛИ КОРРЕКТНОСТЬ УКАЗАННОГО ПУТИ\n";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Методы для задачи 2
    static void saveGame(String path, GameProgress[] gameProgresses) {
        for (int i = 0; i < gameProgresses.length; i++) {
            try (FileOutputStream fileSave = new FileOutputStream(path + i + ".dat"); ObjectOutputStream obj = new ObjectOutputStream(fileSave);) {

                obj.writeObject(gameProgresses[i]);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static boolean zipFiles(String zipPath, File[] fileList) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (File file : fileList) {
                FileInputStream fis = new FileInputStream(file.getPath());
                ZipEntry entry = new ZipEntry(file.getName());
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
                file.delete();
            }
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        //Создаем папки и файлы
        sb.append(createFolder(MAIN_PATH + "src"));
        sb.append(createFolder(MAIN_PATH + "src\\main"));
        sb.append(createFile(MAIN_PATH + "src\\main\\Main.java"));
        sb.append(createFile(MAIN_PATH + "src\\main\\Utils.java"));
        sb.append(createFolder(MAIN_PATH + "src\\test"));
        sb.append(createFolder(MAIN_PATH + "res"));
        sb.append(createFolder(MAIN_PATH + "res\\drawables"));
        sb.append(createFolder(MAIN_PATH + "res\\vectors"));
        sb.append(createFolder(MAIN_PATH + "res\\icons"));
        sb.append(createFolder(MAIN_PATH + "savegames"));
        sb.append(createFolder(MAIN_PATH + "temp"));

        //Записываем сформированные логи в файл
        FileWriter writer;
        try {
            File logFile = new File(MAIN_PATH + "temp\\temp.txt");
            writer = new FileWriter(logFile);
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Задание 2 - Сохранение(Сериализация)
        GameProgress[] progresses = new GameProgress[]{new GameProgress(77, 3, 44, 15.2), new GameProgress(23, 8, 7, 76.9), new GameProgress(54, 12, 32, 12.3)};

        saveGame(MAIN_PATH + "savegames\\save", progresses);

        File dir = new File(MAIN_PATH + "savegames");

        File[] fileList = null;
        if (dir.isDirectory()) {
            fileList = dir.listFiles();
        }

        zipFiles(MAIN_PATH + "savegames\\save.zip", fileList);
    }
}