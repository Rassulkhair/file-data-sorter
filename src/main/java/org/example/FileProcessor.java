package org.example;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class FileProcessor {

    private BufferedReader bufferedReader;
    private int currentFileIndex = 0;
    private boolean allFilesRead = false;
    private String nextLineBuffer;

    private List<File> fileList = new ArrayList<>();

    public FileProcessor() {}

    // Загружает файлы и возвращает все строки
    public List<String> processFile(List<String> resourceNames) throws URISyntaxException {
        List<String> processedLines = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();

        // Загружаем файлы из ресурсов
        for (String name : resourceNames) {
            File file = new File(classLoader.getResource(name).toURI());
            fileList.add(file);
        }

        // Считываем строки
        while (hasNextLine()) {
            processedLines.add(nextLine());
        }

        return processedLines;
    }

    // Проверяет, есть ли следующая строка
    private boolean hasNextLine() {
        try {
            if (allFilesRead) {
                return false;
            }

            while (bufferedReader == null && currentFileIndex < fileList.size()) {
                try {
                    bufferedReader = new BufferedReader(new FileReader(fileList.get(currentFileIndex)));
                } catch (FileNotFoundException e) {
                    currentFileIndex++;
                }
            }

            if (bufferedReader == null) {
                allFilesRead = true;
                return false;
            }

            String line = bufferedReader.readLine();

            if (line != null) {
                nextLineBuffer = line;
                return true;
            } else {
                bufferedReader.close();
                bufferedReader = null;
                currentFileIndex++;
                return hasNextLine();
            }

        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла: " + e.getMessage(), e);
        }
    }

    // Возвращает ранее считанную строку
    private String nextLine() {
        if (nextLineBuffer == null) {
            throw new NoSuchElementException("Больше нет строк для чтения");
        }
        String result = nextLineBuffer;
        nextLineBuffer = null;
        return result;
    }
}
