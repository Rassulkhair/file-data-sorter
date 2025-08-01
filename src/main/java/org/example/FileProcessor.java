package org.example;

import java.io.*;
import java.util.List;
import java.util.NoSuchElementException;

public class FileProcessor {
    // хранение файлов в виде списка
    private final List<File> files;

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
            // Делаем проверку, прочитаны ли все файлы
            if (allFilesRead){
                return false;
            }
            // Пока еще не открыт ни один файл и есть какие-то файлы в списке
            while (bufferedReader==null && currentFileIndex<files.size()){
                // пытаемся открыть текущий файл по индексу, если файл не найден или возникла какая-то иная ошибка
                // то открываем следующий файл (он ведь в цикле while, он будет пробегаться до тех пор, пока это возможно)
                try {
                    bufferedReader = new BufferedReader(new FileReader(files.get(currentFileIndex)));
                } catch (FileNotFoundException e) {
                    currentFileIndex++;
                }
            }

            // это защитный блок, если все файлы оказались недоступны/перебрали все файлы, то мы говорим, что все файлы прочитаны
            if (bufferedReader==null){
                allFilesRead = true;
                return false;
            }


            //В этом блоке кода, мы уже заходим в сам файл и читаем его построчно. Создадим "линию", которая будет у нас в дальнейшем переключатся
            String line = bufferedReader.readLine();

            // если строка есть, то:
            if (line!=null){
                //сохраняем строку в эту переменную, чтобы в будущем ее вернуть
            nextLineBuffer = line;
            return true;
            // если все таки все строки файлы закончились, то тогда мы закрываем поток и переходим к следующему файлу
            } else {
                bufferedReader.close();
                bufferedReader= null;
                currentFileIndex++;
                return hasNextLine();
            }

            //если что-то при чтении пошло не так, то выкидываем исключение с сообщением
        }catch (IOException exception){
            throw new RuntimeException("Ошибка чтения файла: " + exception.getMessage(), exception);
        }

    }

    // переключение на следующую строку
    public String nextLine() {
        // проверяем, в файле оставил ли поток непрочитанные строки
        if (nextLineBuffer == null) {
            throw new NoSuchElementException("Больше нет строк для чтения");
        }
        // берем ранее сохраненную строку
        String result = nextLineBuffer;
        // сбрасываем буфер, чтобы по случайности не использовать прошлые данные
        nextLineBuffer = null;
        //вызываем строку
        return result;
    }
}
