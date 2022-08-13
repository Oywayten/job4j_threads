package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

/**
 * Класс парсит файл
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1
 * @since 12.08.2022
 */
public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() {
        return content(integer -> true);
    }


    public synchronized String getContentWithoutUnicode() {
        return content(integer -> integer < 0x80);
    }

    private String content(Predicate<Integer> filter) {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new FileInputStream(file);
             BufferedInputStream in = new BufferedInputStream(i)) {
            int data;
            while ((data = in.read()) != -1) {
                if (filter.test(data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}