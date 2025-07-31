package org.example;

import java.io.*;
import java.util.List;
import java.util.NoSuchElementException;

public class FileProcessor {
    // хранение файлов в виде списка
    private List<File> files;

    // объект пригодится для того, чтобы читать строки с файла
    private BufferedReader bufferedReader;

    // индекс для файлов, чтобы переключатся на следующий
    private int currentFileIndex;

    // флаг, чтобы понимать, что все файлы прочитаны
    private boolean allFilesRead = false;

    // переменная, где будет храниться прочитанная строка
    private String nextLineBuffer;

    // создаем объект, который подготовит список файлов к чтению
    public FileProcessor(List<File> files) {
        this.files = files;
        this.currentFileIndex = 0;
        this.allFilesRead = files.isEmpty(); // если файлов нет — флаг true
    }

    // метод для проверки, есть ли строки в файле
    public boolean hasNextLine() {
        try {
            if (allFilesRead){
                return false;
            }
            while (bufferedReader==null && currentFileIndex<files.size()){

                try {
                    bufferedReader = new BufferedReader(new FileReader(files.get(currentFileIndex)));
                } catch (FileNotFoundException e) {
                    currentFileIndex++;
                }
            }

            if (bufferedReader==null){
                allFilesRead = true;
                return false;
            }

            String line = bufferedReader.readLine();

            if (line!=null){
            nextLineBuffer = line;
            return true;
            } else {
                bufferedReader.close();
                bufferedReader= null;
                currentFileIndex++;
                return hasNextLine();
            }

        }catch (IOException exception){
            throw new RuntimeException("Ошибка чтения файла: " + exception.getMessage(), exception);
        }

    }

    // переключение на следующую строку
    public String nextLine() {
        if (!hasNextLine()) {
            throw new NoSuchElementException("Больше нет строк для чтения");
        }
        String result = nextLineBuffer;
        nextLineBuffer = null;
        return result;
    }
}
