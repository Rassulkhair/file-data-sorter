package org.example;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {
    // хранение файлов в виде списка
    private List<File> files;

        // объект пригодится для того, чтобы читать строки с файла
    private BufferedReader bufferedReader;

    // индекс для файлов, чтобы переключатся на следующий
    private int currentFileIndex;

    // флаг, чтобы понимать, что все файлы прочитаны
    private boolean allFilesRead = false;

    // создаем объект, который подготовит список файлов к чтению
    public FileProcessor(List<File> files) {
        this.files = files;
        this.currentFileIndex = 0;
        this.allFilesRead = files.isEmpty(); // если файлов нет — флаг true
    }


}
